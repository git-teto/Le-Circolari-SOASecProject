/*
package soasecrgg.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import soasecrgg.config.WelcomeClient;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final WelcomeClient welcomeClient;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
*/
    /*
    @GetMapping("/")
    public String welcome(Authentication authentication) {

        String authorities = authentication.getName() + " - " + authentication.getAuthorities().toString();
        String welcome = welcomeClient.getWelcome();
        return "<h1>" +  welcome + "</h1><h2>" + authorities + "</h2>";
    }
    */

/*
    @GetMapping("/")
    public String token(Authentication authentication){
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService
                .loadAuthorizedClient(oauth2AuthenticationToken.getAuthorizedClientRegistrationId(), oauth2AuthenticationToken.getName());
        String jwtAccessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        String jwtRefreshToken = oAuth2AuthorizedClient.getRefreshToken().getTokenValue();
        return "<b>Access Token: </b>" + jwtAccessToken + "<br/></br><b>Refresh Token: </b>" + jwtRefreshToken;
    }

    @GetMapping("idtoken")
    public String idtoken(@AuthenticationPrincipal OidcUser oidcUser){
        OidcIdToken oidcIdToken = oidcUser.getIdToken();
        String idTokenValue = oidcIdToken.getTokenValue();
        return "<b>Id Token: </b>" + idTokenValue;
    }
}
*/