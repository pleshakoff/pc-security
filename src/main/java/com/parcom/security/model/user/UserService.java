package com.parcom.security.model.user;

import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public  User getCurrentUser(){
        return userRepository.findById(UserUtils.getIdUser()).orElseThrow(EntityExistsException::new);
    }


    User create(UserCreateDto userCreateDto) {
        if (!userCreateDto.getPassword().equals(userCreateDto.getPasswordConfirm())) {
            throw new RuntimeException("Password and confirmation must be equal");
        }
        if (userCreateDto.getIdGroup() == null) {
            throw new RuntimeException("Empty group");
        }

        if (userRepository.findUserByUsername(userCreateDto.getEmail()) != null) {

            throw  new RuntimeException("Email already exists");

        }

        User user = User.builder().
                                   id(userCreateDto.getId()).
                                   username(userCreateDto.getEmail()).
                                   password(passwordEncoder.encode(userCreateDto.passwordConfirm)).
                                   role(userCreateDto.getRole()).
                                   enabled(true).
                                   idGroup(userCreateDto.getIdGroup()).
                                   build();

        return userRepository.save(user);
    }


    @Secured({"ROLE_ADMIN"})
    void delete(Long id)
    {
        userRepository.deleteById(id); ;
    }


}
