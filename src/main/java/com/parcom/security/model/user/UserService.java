package com.parcom.security.model.user;

import com.parcom.exceptions.ForbiddenParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.rest_template.RestTemplateUtils;
import com.parcom.security.auth.TokenResource;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${parcom.services.classroom.host}")
    private String classRoomHost;

    @Value("${parcom.services.classroom.port}")
    private String classRoomPort;

    private  final RestTemplate restTemplate;


    private User getCurrentUser(){
        return userRepository.findById(UserUtils.getIdUser()).orElseThrow(EntityExistsException::new);
    }


    User create(UserCreateDto userCreateDto) {
        if (!userCreateDto.getPassword().equals(userCreateDto.getPasswordConfirm())) {
            throw new ParcomException("user.password_confirm_not_equal");
        }
        if (userCreateDto.getIdGroup() == null) {
            throw new ParcomException("group.can_not_be_null");
        }

        if (userRepository.findUserByUsername(userCreateDto.getEmail()) != null) {

            throw  new ParcomException("user.duplicate_email");

        }

        User user = User.builder().
                                   id(userCreateDto.getId()).
                                   username(userCreateDto.getEmail()).
                                   password(passwordEncoder.encode(userCreateDto.passwordConfirm)).
                                   role(userCreateDto.getRole()).
                                   enabled(true).
                                   idGroup(userCreateDto.getIdGroup()).
                                   idStudent(userCreateDto.getIdStudent()).
                                   build();

        return userRepository.save(user);
    }



    void delete(Long id)
    {
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new ForbiddenParcomException();

        userRepository.deleteById(id); ;
    }

   public User setContext(Long idGroup, Long idStudent) {

        if (!UserUtils.getIdGroup().equals(idGroup)) {
            checkGroup(idGroup);
        }

        if (!Objects.equals(idStudent, UserUtils.getIdStudent()))
        {
            checkStudent(idStudent);
        }

        User user = getCurrentUser();
        user.setIdGroup(idGroup);
        user.setIdStudent(idStudent);
        return userRepository.save(user);
    }

    private void checkGroup(Long idGroup) {
        log.info("Checking id group");
        URI uri = UriComponentsBuilder.newInstance().scheme(RestTemplateUtils.scheme).
                host(classRoomHost).
                port(classRoomPort).path("/group/my").build().toUri();

        ResponseEntity<Group[]> groupResponseEntity =restTemplate.exchange(uri, HttpMethod.GET, RestTemplateUtils.getHttpEntity(), Group[].class);
        if (groupResponseEntity.getStatusCode()== HttpStatus.OK) {
            Group[] groups = groupResponseEntity.getBody();
            if (groups == null|| Arrays.stream(groups).map(Group::getId).noneMatch(idGroup::equals)) {
                throw new EntityNotFoundException("group.can_not_be_chosen");
            }
        }
        else
        {
            throw new RuntimeException(groupResponseEntity.getBody().toString());
        }
    }

    private void checkStudent(Long idStudent) {
        log.info("Checking id student");
        if (idStudent == null) {
            return;
        }

        URI uri = UriComponentsBuilder.newInstance().scheme(RestTemplateUtils.scheme).
                host(classRoomHost).
                port(classRoomPort).path("/students/my").build().toUri();

        ResponseEntity<Student[]> groupResponseEntity =restTemplate.exchange(uri, HttpMethod.GET, RestTemplateUtils.getHttpEntity(), Student[].class);
        if (groupResponseEntity.getStatusCode()== HttpStatus.OK) {
            Student[] students = groupResponseEntity.getBody();
            if (students == null||Arrays.stream(students).map(Student::getId).noneMatch(idStudent::equals)) {
                throw new EntityNotFoundException("student.can_not_be_chosen");
            }
        }
        else
        {
            throw new RuntimeException(groupResponseEntity.getBody().toString());
        }
    }


    public User findUserByUsername(String username) {
        return  userRepository.findUserByUsername(username);
    }
}
