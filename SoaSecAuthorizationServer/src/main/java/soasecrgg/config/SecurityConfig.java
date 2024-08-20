/*
 * Il codice è una configurazione di sicurezza per un’applicazione Spring Boot che utilizza OAuth2 e
 * JWT (JSON Web Tokens) per l’autenticazione e l’autorizzazione.
 */

package soasecrgg.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {

    /**
     * Questo codice definisce un bean Spring che configura la sicurezza dell'applicazione.
     * Protegge le risorse dell'applicazione utilizzando l'autenticazione OAuth 2.0 e
     * l'autorizzazione basata su token JWT, fornendo un livello di sicurezza completo.
     * @param http Un oggetto HttpSecurity che rappresenta la configurazione di sicurezza. Permette di personalizzare
     *             la configurazione della sicurezza per l'applicazione.
     * @return Un oggetto SecurityFilterChain che rappresenta la catena di filtri di sicurezza da applicare alle richieste HTTP.
     *         Questa catena implementa le varie configurazioni di sicurezza definite nel metodo.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain asSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        return http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(withDefaults())
                .and()
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) 
                .build();
    }

    /**
     * Questo codice definisce un secondo bean Spring che configura una catena di filtri di sicurezza per l'applicazione.
     * Abilita l'autenticazione tramite form login, permettendo agli utenti di accedere con username e password.
     * Stabilisce che tutte le richieste debbano essere effettuate da utenti autenticati.
     * La configurazione precedente (@Order(1)) si occupa di impostare l'autenticazione e l'autorizzazione basate su OAuth 2.0,
     * mentre questo metodo (@Order(2)) fornisce un livello di sicurezza aggiuntivo basato sull'autenticazione standard tramite form login.
     * @param http Un oggetto HttpSecurity che rappresenta la configurazione di sicurezza.
     *             Permette di personalizzare la configurazione della sicurezza per l'applicazione.
     * @return Un oggetto SecurityFilterChain che rappresenta la catena di filtri di sicurezza da applicare alle richieste HTTP.
     *         Questa catena implementa le varie configurazioni di sicurezza definite nel metodo.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(withDefaults()) 
                .authorizeHttpRequests(authorize ->authorize.anyRequest().authenticated()) 
                .build();

    }


    /**
     * Il metodo passwordEncoder definisce un PasswordEncoder che utilizza BCryptPasswordEncoder.
     * Questo viene utilizzato per codificare le password in modo sicuro.
     * @return Crea e restituisce una nuova istanza di BCryptPasswordEncoder.
     *         Questa è l'implementazione predefinita utilizzata da Spring Security per codificare le password in modo sicuro.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Il metodo registeredClientRepository definisce un RegisteredClientRepository che memorizza
     * i dettagli del client OAuth2. Questi dettagli includono l’ID del client, il segreto del client,
     * l’URI di reindirizzamento, i metodi di autenticazione del client, i tipi di concessione di
     * autorizzazione e le impostazioni del client.
     * @return Oggetto RegisteredClientRepository che fornisce l'accesso e la gestione dei client OAuth 2.0
     *         registrati con l'applicazione.
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret(passwordEncoder().encode("secret"))
                .scope("read")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/myoauth2")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .clientSettings(clientSettings())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * Definisce un bean con impostazioni di base del server di autorizzazione.
     * @return Oggetto AuthorizationServerSettings contenente i dettagli di configurazione per il server di autorizzazione.
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /**
     *Aggiunge un livello di sicurezza extra all'autenticazione dei client
     * @return Restituisce un oggetto ClientSettings che contiene impostazioni applicate ai client registrati.
     */
    @Bean
    ClientSettings clientSettings() {
        return ClientSettings.builder()
                .requireProofKey(true)
                .build();
    }


    /**
     * Il metodo tokenCustomizer definisce un OAuth2TokenCustomizer che personalizza
     * i token JWT emessi. Aggiunge rivendicazioni personalizzate ai token di accesso
     * e di identità.
     * @return Restituisce una funzione lambda che definisce la logica di personalizzazione dei claim.
     */
    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            Authentication principal = context.getPrincipal();
            Set<String> authorities = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()); 
            if (context.getTokenType().getValue().equals("id_token")) { 
            	context.getClaims().claim("SOASec", "IdSOASec")
                        .claim("authorities", authorities)
                        .claim("user", principal.getName());
            }
            if (context.getTokenType().getValue().equals("access_token")) {
                context.getClaims().claim("SOASec", "SOASec");
                context.getClaims().claim("authorities", authorities);
            }

        };
    }

    /**
     * Questo codice abilita la decodifica di JWT nel tuo server di autorizzazione OAuth2.
     * Il decoder utilizza le chiavi pubbliche JWK fornite dalla sorgente jwkSource per verificare la firma dei token ricevuti.
     * @param jwkSource Oggetto che fornisce le chiavi pubbliche JSON Web Key (JWK) utilizzate per la verifica della firma dei JWT.
     * @return JwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


    /**
     * Permette di configurare una JWKSource per il server di autorizzazione OAuth2.
     * Crea dinamicamente una chiave RSA e la rende disponibile per la verifica dei token JWT.
     * @return ritorna una funzione lamda che implementa l'interfaccia JWKSource<SecurityContext>.
     * Utilizzata dal server di autorizzazione OAuth2 per recuperare la chiave JWK necessaria
     * per la verifica della firma di un token JWT.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }


    /**
     * Il metodo generateRsa genera una coppia di chiavi RSA che vengono utilizzate
     * per la firma e la verifica dei token JWT.
     * @return Oggetto RSAKey che contiene entrambe le chiavi e un identificativo casuale.
     * L'oggetto RSAKey utilizzato successivamente per firmare o verificare token JWT.
     */
    public static RSAKey generateRsa() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

    
    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
