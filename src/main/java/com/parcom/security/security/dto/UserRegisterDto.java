package com.parcom.security.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserRegisterDto {

    @NotNull
    private final String username;

    @NotNull
    private final String firstName;

    private final String middleName;

    @NotNull
    private final String familyName;

    @NotNull
    private final String email;

    @NotNull
    private final String phone;

    @NotNull
    private final String password;

    @NotNull
    private final String passwordConfirm;
    
    @NotNull
    private final  Long idGroup;

    @NotNull
    private final  String role;


    




}