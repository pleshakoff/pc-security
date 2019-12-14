package com.parcom.security.auth;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class ContextDto {

    @NotNull
    private final Long idGroup;

    private final Long idStudent;

}
