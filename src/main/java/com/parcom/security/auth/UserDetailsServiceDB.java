package com.parcom.security.auth;


import com.parcom.security.model.user.UserRepository;
import com.parcom.security.model.user.User;
import com.parcom.security_client.UserDetailsPC;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class UserDetailsServiceDB implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    private final MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user =repo.findUserByUsername(username);
       if (user !=null) {

           Set<GrantedAuthority> authorities = new HashSet<>();
           authorities.add(new SimpleGrantedAuthority(user.getRole()));
           return new UserDetailsPC(user.getUsername(),user.getPassword(),user.getId(),authorities,user.isEnabled(),user.getIdGroup());
       }
       else
         throw  new BadCredentialsException(messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null,"Bad credentials" ,LocaleContextHolder.getLocale()));
    }


}