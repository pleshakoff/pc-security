package com.parcom.security.security;

import com.parcom.security.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsPC implements UserDetails {

    private final String username;
    private final String password;
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final Long idGroup;


    UserDetailsPC(User user) {
        username= user.getUsername();
        password= user.getPassword();
        id = user.getId();

        Set<GrantedAuthority> a = new HashSet<>();
            a.add(new SimpleGrantedAuthority(user.getRole()));
        authorities = a;
        enabled = user.isEnabled();
        idGroup = user.getIdGroup();
     }

    public UserDetailsPC(String username, Long id, Collection<? extends GrantedAuthority> authorities, Long idGroup) {
        this.username = username;
        this.id = id;
        this.authorities = authorities;
        this.idGroup = idGroup;
        password = null;
        enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
             return authorities;
    }

    String getAuthoritiesStr() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getIdGroup() {
        return idGroup;
    }


}
