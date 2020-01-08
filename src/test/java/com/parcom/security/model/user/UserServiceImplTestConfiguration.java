package com.parcom.security.model.user;

import com.parcom.network.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class UserServiceImplTestConfiguration {

    @Bean
    UserService userService(UserRepository userRepository, Network network,  PasswordEncoder passwordEncoder) {
        return  new UserServiceImpl(userRepository,passwordEncoder,network);
    }
}
