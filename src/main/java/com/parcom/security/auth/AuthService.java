package com.parcom.security.auth;

import com.parcom.security.register.UserRegisterDto;
import com.parcom.security.model.user.User;
import com.parcom.security.model.user.UserRepository;
import com.parcom.security_client.UserDetailsPC;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    TokenResource authenticate(UserAuthDto userAuthDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsPC userDetail = (UserDetailsPC) authentication.getPrincipal();
        return new TokenResource(TokenCreate.createToken(userDetail), userDetail.getId());
    }
}
