package com.cdurgun.user;

import java.util.List;
import java.util.UUID;

public class UserArrayDataAccessService implements UserDao {
    private static final List<User> users;

    static {
        users = List.of(
            new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "Cemal"),
            new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Sinan")
        );
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId))
                return user;
        }
        return null;
    }
}
