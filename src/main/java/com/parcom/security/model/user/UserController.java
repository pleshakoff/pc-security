package com.parcom.security.model.user;

import com.parcom.security_client.Checksum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(value = "/register")
    @ApiOperation("Registration")
    public User registerMember(@Valid @RequestBody UserCreateDto userCreateDto,
                                @RequestHeader(required = false) String checksum,
                               BindingResult bindingResult ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Checksum.validateCheckSum(checksum,userCreateDto.getId());
        return userService.create(userCreateDto);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete student")
    public void delete(@PathVariable Long id,
                       @RequestHeader(required = false) String checksum)
    {
        Checksum.validateCheckSum(checksum,id);

        userService.delete(id);
    }





}
