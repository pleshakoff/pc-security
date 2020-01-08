package com.parcom.security.auth;

import com.parcom.security.model.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;


@Configuration
public class AuthServiceImplTestConfiguration {

    @Bean
    AuthService authService(UserService userService, AuthenticationManager authenticationManager) {
        return  new AuthServiceImpl(userService,authenticationManager);
    }
}
