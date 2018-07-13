package com.example.moneyapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.moneyapi.event.RecursoCadastrarEvent;

public class RecursoCadastrarListener implements ApplicationListener<RecursoCadastrarEvent> {

	@Override
	public void onApplicationEvent(RecursoCadastrarEvent event) {
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();

		adicionarHeaderLocation(response, id);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long id) {
		URI locationRedirect = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id)
				.toUri();
		response.setHeader("Location", locationRedirect.toASCIIString());
	}

}
