package com.cdurgun.user;

import java.util.UUID;

public interface UserDao {

    User findById(UUID userId);

    User[] findAll();

}
