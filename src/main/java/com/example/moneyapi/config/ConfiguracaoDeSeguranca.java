package com.example.moneyapi.config;

import com.example.moneyapi.config.filter.OpenIdConnectFilter;
import com.example.moneyapi.config.token.OpenIdTokenServices;
import com.example.moneyapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2RestTemplate openidRestTemplate;

	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private OpenIdTokenServices tokenServices;

	@Autowired
	private OAuth2ProtectedResourceDetails resourceDetails;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] caminhosPermitidos = new String[] { "/", "/pessoas", "/oauth/callback", "/webjars/**", "/static/**",
				"/jquery*" };

		http.addFilterAfter(clientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
				.addFilterAfter(openIdConnectFilter(), OAuth2ClientContextFilter.class).httpBasic()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/oauth/callback")).and()
				.authorizeRequests().antMatchers(caminhosPermitidos).permitAll().anyRequest().authenticated().and()
				.logout().logoutSuccessUrl("/").permitAll().and().csrf().disable();
	}

	@Bean
	public OpenIdConnectFilter openIdConnectFilter() {
		OpenIdConnectFilter filter = new OpenIdConnectFilter("/categorias/**", "/oauth/callback");

		filter.setRestTemplate(openidRestTemplate);
		filter.setJsonMapper(jsonMapper);
		filter.setUsuarioRepository(usuarioRepository);
		filter.setTokenServices(tokenServices);
		filter.setResourceDetails(resourceDetails);
		return filter;
	}

	private OAuth2ClientContextFilter clientContextFilter() {
		return new OAuth2ClientContextFilter();
	}

}
