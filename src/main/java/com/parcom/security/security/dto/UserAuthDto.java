package com.parcom.security.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserAuthDto {

    private final String username;
    private String password;

}