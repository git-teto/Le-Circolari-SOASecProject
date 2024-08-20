package soasecrgg.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import soasecrgg.handler.MySimpleUrlAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public MySimpleUrlAuthenticationSuccessHandler successHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *La pagina di login è impostata su "/oauth2/authorization/myoauth2".
     * Un gestore di successo personalizzato (successHandler()) viene utilizzato per gestire i login riusciti (non fornito in questo snippet).
     * Il servizio utente OIDC (this.oidcUserService()) viene utilizzato per recuperare le informazioni utente dal provider OAuth2.
     * L'URL di logout è impostato su "/logout".
     * Dopo un logout riuscito, gli utenti vengono reindirizzati a "http://localhost:8080/".
     * @param http Questo oggetto fornisce metodi per configurare vari aspetti di Spring Security,
     *             come l'autorizzazione, l'autenticazione e la protezione CSRF.
     * @param clientRegistrationRepository Questo oggetto viene utilizzato per memorizzare informazioni sui client OAuth 2.0
     *                                     con cui l'applicazione può interagire.
     * @return SecurityFilterChain il metodo ritorna un bean di tipo SecurityFilterChain che rappresenta questa configurazione.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {

        String base_uri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
        DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, base_uri);
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());

        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/amministratore").hasAuthority("amministratore")
                        .requestMatchers("/amministratore/api/**").hasAuthority("amministratore")
                        .requestMatchers("/docente").hasAuthority("docente")
                        .requestMatchers("/docente/api/**").hasAuthority("docente")
                        .requestMatchers("/studente").hasAuthority("studente")
                        .requestMatchers("/studente/api/**").hasAuthority("studente")
                        .requestMatchers("/personale_presidenza").hasAuthority("presidenza")
                        .requestMatchers("/personale_presidenza/api/**").hasAuthority("presidenza")
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                )
                .csrf(csrf -> csrf
                        .disable()
                )
                .oauth2Login(oauth2Login -> {
                    oauth2Login.loginPage("/oauth2/authorization/myoauth2");
                    oauth2Login.authorizationEndpoint().authorizationRequestResolver(resolver);
                    oauth2Login.successHandler(successHandler());
                    oauth2Login.userInfoEndpoint(userInfo -> userInfo
                            .oidcUserService(this.oidcUserService()));
                })
                .oauth2Client(withDefaults())
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://localhost:8080/");
        return http.build();
    }


    GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (OidcUserAuthority.class.isInstance(authority)) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;
                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
                    if (idToken.hasClaim("authorities")) {
                        Collection<String> userAuthorities = idToken.getClaimAsStringList("authorities");
                        mappedAuthorities.addAll(userAuthorities.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList());
                    }
                }
            });
            return mappedAuthorities;
        };
    }

    /**
     *Questo metodo personalizzato sfrutta il OidcUserService predefinito per il caricamento di base dell'utente
     * e quindi estrae e mappa le autorizzazioni dell'utente dal token di accesso utilizzando l'analisi JWT
     * @return ritorna una funzione lambda, una funzione anonima definita in linea, che genera un OidcUser
     * con le autorizzazioni estratte dal token di accesso e mappate in base alle necessità dell'applicazione.
     */
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            try {
                JWT jwt = JWTParser.parse(accessToken.getTokenValue());
                JWTClaimsSet claimSet = jwt.getJWTClaimsSet();
                Collection<String> userAuthorities = claimSet.getStringListClaim("authorities");
                mappedAuthorities.addAll(userAuthorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());
            } catch (ParseException e) {
                System.err.println("Error OAuth2UserService: " + e.getMessage());
            }
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            return oidcUser;
        };
    }
}
