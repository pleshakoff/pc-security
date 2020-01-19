package com.parcom.security.model.user;

import com.parcom.exceptions.ForbiddenParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.network.Network;
import com.parcom.security.SpringSecurityTestConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Optional;

import static com.parcom.security.SpringSecurityTestConfiguration.ID_USER_ADMIN;
import static com.parcom.security_client.UserUtils.ROLE_ADMIN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserServiceImplTestConfiguration.class, SpringSecurityTestConfiguration.class})
class UserServiceImplTest {

    private static final long ID_STUDENT = 1L;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final long ID_GROUP = 1L;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    Network network;


    @Autowired
    UserService userService;

    @Test
    void create() {

        User user = User.builder().
                idStudent(ID_STUDENT).
                username(USERNAME).
                enabled(true).
                role(ROLE_ADMIN).
                idGroup(ID_GROUP).
                password(PASSWORD).
                id(ID_USER_ADMIN).build();

        Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(ID_USER_ADMIN).
                email(USERNAME).
                idGroup(ID_GROUP).
                idStudent(ID_STUDENT).
                password(PASSWORD).
                passwordConfirm(PASSWORD).
                role(ROLE_ADMIN).
                build();

        User userCreated = userService.create(userCreateDto);

        assertAll(
           () ->  assertEquals(USERNAME,userCreated.getUsername()),
           () ->  assertEquals(ID_GROUP,userCreated.getIdGroup()),
           () ->  assertEquals(ID_STUDENT,userCreated.getIdStudent()),
           () -> assertEquals(ROLE_ADMIN,userCreated.getRole())
        );
    }


    @Test
    void createWrongPasswordConfirmation() {

        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(ID_USER_ADMIN).
                email(USERNAME).
                idGroup(ID_GROUP).
                idStudent(ID_STUDENT).
                password(PASSWORD).
                passwordConfirm("secret").
                role(ROLE_ADMIN).
                build();

        assertThrows(ParcomException.class,() ->  userService.create(userCreateDto));
    }

    @Test
    void createEmptyGroup() {
        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(ID_USER_ADMIN).
                email(USERNAME).
                idStudent(ID_STUDENT).
                password(PASSWORD).
                passwordConfirm(PASSWORD).
                role(ROLE_ADMIN).
                build();

        assertThrows(ParcomException.class,() ->  userService.create(userCreateDto));
    }


    @Test
    void createUserExists() {


        User user = User.builder().
                idStudent(ID_STUDENT).
                username(USERNAME).
                enabled(true).
                role(ROLE_ADMIN).
                idGroup(ID_GROUP).
                password(PASSWORD).
                id(ID_USER_ADMIN).build();

        Mockito.when(userRepository.findUserByUsername(USERNAME)).thenReturn(user);



        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(ID_USER_ADMIN).
                email(USERNAME).
                idGroup(ID_GROUP).
                idStudent(ID_STUDENT).
                password(PASSWORD).
                passwordConfirm(PASSWORD).
                role(ROLE_ADMIN).
                build();

        assertThrows(ParcomException.class,() ->  userService.create(userCreateDto));
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    void delete() {
        userService.delete(ID_USER_ADMIN);
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    void deleteByAnotherUser() {
        assertThrows(ForbiddenParcomException.class,() ->  userService.delete(ID_USER_ADMIN));
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    void setContext() {

        User user = User.builder().
                idStudent(ID_STUDENT).
                username(USERNAME).
                enabled(true).
                role(ROLE_ADMIN).
                idGroup(ID_GROUP).
                password(PASSWORD).
                id(ID_USER_ADMIN).build();

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findById (ID_USER_ADMIN)).thenReturn(Optional.of(user));

        User userCreated = userService.setContext(ID_GROUP,ID_STUDENT);

        assertAll(
                () ->  assertEquals(USERNAME,userCreated.getUsername()),
                () ->  assertEquals(ID_GROUP,userCreated.getIdGroup()),
                () ->  assertEquals(ID_STUDENT,userCreated.getIdStudent()),
                () -> assertEquals(ROLE_ADMIN,userCreated.getRole())
        );
   }
}