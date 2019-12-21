package com.parcom.security.auth;


import com.parcom.security.model.user.User;
import com.parcom.security.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserService userService;


    private final AuthenticationManager authenticationManager;


    @Override
    public TokenResource authenticate(UserAuthDto userAuthDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResource(TokenCreate.createToken((UserDetails) authentication.getPrincipal()));
    }


    @Override
    public TokenResource setContext(ContextDto contextDto) {
        User user = userService.setContext(contextDto.getIdGroup(), contextDto.getIdStudent());


        String token = TokenCreate.createToken(UserDetailsServiceDB.buildUserDetails(user));
        return new TokenResource(token);

    }
}