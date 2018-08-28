package com.example.moneyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

import static java.util.Arrays.asList;

@Configuration
@EnableOAuth2Client
public class OAuthClientConfig {

	@Autowired
	private DiscoveryDocument discoveryDocument;

	@Bean
	public OAuth2ProtectedResourceDetails oauthClientResourceDetails() {
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();

		details.setClientId(discoveryDocument.getClientId());
		details.setClientSecret(discoveryDocument.getClientSecret());
		details.setAccessTokenUri(discoveryDocument.getAccessTokenUri());
		details.setUserAuthorizationUri(discoveryDocument.getUserAuthorizationUri());
		details.setPreEstablishedRedirectUri(discoveryDocument.getRedirectUri());
		details.setScope(asList("openid", "public_profile", "email"));
		details.setUseCurrentUri(false);
		return details;
	}

	/**
	 * obter os tokens de autenticação e de acesso
	 * 
	 * @param clientContext
	 * @return
	 */
	@Bean
	public OAuth2RestTemplate oauthClientRestTemplate(OAuth2ClientContext clientContext) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(oauthClientResourceDetails(), clientContext);
		template.setAccessTokenProvider(getAccessTokenProvider());
		return template;
	}

	private AccessTokenProviderChain getAccessTokenProvider() {
		return new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
	}
}
