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


    User create(User user) {
        if (user.getIdGroup() == null) {
            throw new RuntimeException("Необходимо выбрать класс");
        }
        return userRepository.save(user);
    }


    @Secured({"ROLE_ADMIN"})
    void delete(Long id)
    {
        userRepository.deleteById(id); ;
    }


}
