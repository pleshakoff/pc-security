package com.parcom.security.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(@Param("username") String username);

}
