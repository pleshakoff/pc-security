package com.parcom.security.auth;

import com.parcom.security.model.user.User;
import com.parcom.security.model.user.UserCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/auth", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(value = "/login")
    @ApiOperation("Get user's session token")
    public TokenResource authenticate(@Valid @RequestBody UserAuthDto userAuthDTO,
                                      BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authService.authenticate(userAuthDTO);
    }

    @PutMapping(value = "/context")
    @ApiOperation("Set for the user another group or student")
    public TokenResource setContext(@Valid @RequestBody ContextDto contextDto,
                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authService.setContext(contextDto);
    }







}
