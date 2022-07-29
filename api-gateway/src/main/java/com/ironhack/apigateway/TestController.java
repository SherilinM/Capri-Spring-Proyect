package com.ironhack.apigateway;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/greeting")
    public String greeting(@AuthenticationPrincipal OidcUser oidcUser, Model model,
                           @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client) {
        System.out.println("====START==== controller from" + oidcUser.toString());
        model.addAttribute("username", oidcUser.getEmail());
        model.addAttribute("idToken", oidcUser.getIdToken());
        model.addAttribute("accessToken", client.getAccessToken());

        return model.toString();
    }
}
