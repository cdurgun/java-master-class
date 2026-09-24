package com.cdurgun.user;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    User findById(UUID userId);

    List<User> findAll();

}
