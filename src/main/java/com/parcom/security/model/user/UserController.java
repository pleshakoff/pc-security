package com.parcom.security.model.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/users", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(value = "/register")
    @ApiOperation("Registration")
    public User registerMember(@Valid @RequestBody UserCreateDto userCreateDto,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return userService.create(userCreateDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete student")
    public void delete(@PathVariable Long id)
    {
        userService.delete(id);
    }





}
