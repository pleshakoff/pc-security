package com.parcom.security.auth;

import com.parcom.security.SpringSecurityTestConfiguration;
import com.parcom.security.model.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {AuthServiceImplTestConfiguration.class, SpringSecurityTestConfiguration.class})
class AuthServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";


   @Autowired
   AuthService authService;

   @Autowired
   UserDetailsService userDetailsService;

   @MockBean
   UserService userService;

   @MockBean
   AuthenticationManager authenticationManager;



    @Test
    void authenticate() {
        UserAuthDto userAuthDto = new UserAuthDto(USERNAME, PASSWORD);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsService.loadUserByUsername("admin@parcom.com"));
        Assertions.assertNotNull(authService.authenticate(userAuthDto));

    }

    }