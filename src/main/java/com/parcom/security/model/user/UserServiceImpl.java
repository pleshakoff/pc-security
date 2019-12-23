package com.parcom.security.model.user;

import com.parcom.exceptions.ForbiddenParcomException;
import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.network.Network;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService {

    private static final String SERVICE_NAME_CLASSROOM = "classroom";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final Network network;


    private User getCurrentUser(){
        return userRepository.findById(UserUtils.getIdUser()).orElseThrow(EntityExistsException::new);
    }


    @Override
    public User create(@NotNull UserCreateDto userCreateDto) {
        log.info("Create user account {}",userCreateDto.getEmail());
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



    @Override
    public void delete(Long id)
    {
        log.info("Delete user  {}", UserUtils.getIdUser());
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new ForbiddenParcomException();

        userRepository.deleteById(id); ;
    }

   @Override
   public User setContext(Long idGroup, Long idStudent) {
       log.info("Change context for {}", UserUtils.getIdUser());

        if (!UserUtils.getIdGroup().equals(idGroup)) {
            checkGroup(idGroup);
        }

        if (!Objects.equals(idStudent, UserUtils.getIdStudent()))
        {
            checkStudent(idStudent,idGroup);
        }

        User user = getCurrentUser();
        user.setIdGroup(idGroup);
        user.setIdStudent(idStudent);
        return userRepository.save(user);
    }

    private void checkGroup(Long idGroup) {
        log.info("Checking id group");
        Group[] groups= network.callGet(SERVICE_NAME_CLASSROOM,Group[].class,"group","my").getBody();
        if (groups == null|| Arrays.stream(groups).map(Group::getId).noneMatch(idGroup::equals)) {
            throw new NotFoundParcomException("group.can_not_be_chosen");
        }
    }

    private void checkStudent(Long idStudent,Long idGroup) {
        log.info("Checking id student");
        if (idStudent == null) {
            return;
        }
        MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();
        params.add("idGroup",idGroup.toString());
         Student[] students = network.callGet(SERVICE_NAME_CLASSROOM,Student[].class,params,"students","my").getBody();
        if (students == null||Arrays.stream(students).map(Student::getId).noneMatch(idStudent::equals)) {
            throw new NotFoundParcomException("student.can_not_be_chosen");
        }
    }

}
