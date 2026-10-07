package com.cdurgun.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        return userDao.findAll();
    }

    public boolean userExists(UUID userId) {
        return getUserById(userId).isPresent();
    }

    public Optional<User> getUserById(UUID userId) {
        return userDao.findById(userId);
    }
}
