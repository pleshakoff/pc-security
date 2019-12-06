package com.parcom.security.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResource
{

	private final String token;

	@JsonIgnore
	private final Long id;


}