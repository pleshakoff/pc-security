package com.parcom.security.model.user;

public interface UserService {

    User create(UserCreateDto userCreateDto);

    void delete(Long id);

    User setContext(Long idGroup, Long idStudent);
}
