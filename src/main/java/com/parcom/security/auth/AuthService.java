package com.parcom.security.auth;

import com.parcom.rest_template.RestTemplateUtils;
import com.parcom.security.model.user.User;
import com.parcom.security.model.user.UserRepository;
import com.parcom.security.model.user.UserService;
import com.parcom.security_client.UserDetailsPC;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;


    private final AuthenticationManager authenticationManager;


    TokenResource authenticate(UserAuthDto userAuthDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResource(TokenCreate.createToken((UserDetails) authentication.getPrincipal()));
    }


    TokenResource setContext(ContextDto contextDto) {
        User user = userService.setContext(contextDto.getIdGroup(), contextDto.getIdStudent());


        String token = TokenCreate.createToken(UserDetailsServiceDB.buildUserDetails(user));
        return new TokenResource(token);

    }
}