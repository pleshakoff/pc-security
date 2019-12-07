package com.parcom.security.register;

import com.parcom.security.auth.TokenResource;
import com.parcom.security.auth.UserAuthDto;
import com.parcom.security.model.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Registration")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;


    @PostMapping(value = "/register/")
    @ApiOperation("Registration")
    public User registerMember(@Valid @RequestBody UserRegisterDto registerDto,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return registerService.register(registerDto);
    }




}
