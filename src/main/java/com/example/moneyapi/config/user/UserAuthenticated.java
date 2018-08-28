package com.example.moneyapi.config.user;

import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.example.moneyapi.model.AutenticacaoOpenid;

public class UserAuthenticated implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Getter
	private OAuth2AccessToken token;

	@Getter
	private AutenticacaoOpenid autenticacaoOpenId;

	public UserAuthenticated(AutenticacaoOpenid autenticacaoOpenid, OAuth2AccessToken token) {
		this.token = token;
		this.autenticacaoOpenId = autenticacaoOpenid;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return token.getValue();
	}

	@Override
	public String getUsername() {
		return autenticacaoOpenId.getUsuario().getNome();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !token.isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !autenticacaoOpenId.expirou();
	}

	@Override
	public boolean isEnabled() {
		return !token.isExpired();
	}
}
