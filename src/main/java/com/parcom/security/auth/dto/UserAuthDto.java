package com.parcom.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserAuthDto {

    private final String username;
    private String password;

}