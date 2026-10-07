package com.cdurgun.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    Optional<User> findById(UUID userId);

    List<User> findAll();

}
