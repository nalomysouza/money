package com.example.moneyapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

public class RecursoCadastrarEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	@Getter
	private HttpServletResponse response;
	@Getter
	private Long id;

	public RecursoCadastrarEvent(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.response = response;
		this.id = id;
	}

}
