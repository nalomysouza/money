package com.example.moneyapi.config.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

/**
 * claims que vamos obter a partir do id_token
 * 
 * @author nalomysouza
 *
 */
public class TokenIdClaims {

	@Getter
	@Setter
	@JsonProperty("aud")
	private String audience;

	@Getter
	@Setter
	@JsonProperty("sub")
	private String subjectIdentifier;

	@Getter
	@Setter
	@JsonProperty("iss")
	private String issuerIdentifier;

	@Getter
	@Setter
	@JsonProperty("iat")
	private long issuedAt;

	@Getter
	@Setter
	@JsonProperty("exp")
	private long expirationTime;

	@Getter
	@Setter
	@JsonProperty("auth_time")
	private long authorizationTime;

	public static TokenIdClaims extrairClaims(ObjectMapper jsonMapper, OAuth2AccessToken accessToken) {
		String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
		Jwt tokenDecoded = JwtHelper.decode(idToken);

		try {
			return jsonMapper.readValue(tokenDecoded.getClaims(), TokenIdClaims.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
