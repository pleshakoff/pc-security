package com.parcom.security.security;

import com.parcom.security.model.user.TokenResource;
import com.parcom.security.model.user.User;
import com.parcom.security.model.user.UserRepository;
import com.parcom.security.security.dto.UserAuthDto;
import com.parcom.security.security.dto.UserRegisterDto;
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
    private  final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    User register(UserRegisterDto userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
        }
       if (userDTO.getIdGroup() == null) {
           throw new RuntimeException("Необходимо выбрать класс");
       }

           User user = User.builder().email(userDTO.getEmail()).
                   enabled(true).
                   firstName(userDTO.getFirstName()).
                   middleName(userDTO.getMiddleName()).
                   familyName(userDTO.getFamilyName()).
                   username(userDTO.getUsername()).
                   phone(userDTO.getPhone()).
                   password(passwordEncoder.encode(userDTO.getPassword())).
                   role(userDTO.getRole()).
                   idGroup(userDTO.getIdGroup()).build();


       return userRepository.save(user);

   }



    TokenResource authenticate(UserAuthDto userAuthDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsPC userDetail = (UserDetailsPC) authentication.getPrincipal();
        return new TokenResource(TokenCreate.createToken(userDetail),userDetail.getId());
    }
}
