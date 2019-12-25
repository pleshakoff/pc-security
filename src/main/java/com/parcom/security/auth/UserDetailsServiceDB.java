package com.parcom.security.auth;


import com.parcom.security.model.user.UserRepository;
import com.parcom.security.model.user.User;
import com.parcom.security_client.UserDetailsPC;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
           return buildUserDetails(user);
       }
       else
         throw  new BadCredentialsException(messageSource.getMessage("user.bad_credentials", null,"Bad credentials" ,LocaleContextHolder.getLocale()));
    }

    static UserDetails buildUserDetails(User user) {

        return new UserDetailsPC(user.getUsername(),
                user.getPassword(),
                user.getId(),
                AuthorityUtils.createAuthorityList(user.getRole()),
                user.isEnabled(),
                user.getIdGroup(),
                user.getIdStudent());
    }


}
