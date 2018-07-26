package com.example.moneyapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryDocument {

	@Getter
	@Value("${logincidadao.client_id}")
	private String clientId;

	@Getter
	@Value("${logincidadao.client_secret}")
	private String clientSecret;

	@Getter
	@Value("${logincidadao.access_token_uri}")
	private String accessTokenUri;

	@Getter
	@Value("${logincidadao.user_authorization_uri}")
	private String userAuthorizationUri;

	@Getter
	@Value("${logincidadao.redirect_uri}")
	private String redirectUri;

	@Getter
	@Value("${logincidadao.userinfo_endpoint}")
	private String userInfoEndpoint;

}
