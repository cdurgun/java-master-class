package com.cdurgun.user;

import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] getUsers() {
        return userDao.findAll();
    }

    public boolean userExists(UUID userId) {
        return getUserById(userId) != null;
    }

    public User getUserById(UUID userId) {
        return userDao.findById(userId);
    }
}
