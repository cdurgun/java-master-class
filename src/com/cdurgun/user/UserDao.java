package com.cdurgun.user;

import java.util.UUID;

public class UserDao {

    private final static User[] users;

    static {
        users = new User[] {
            new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "Cemal"),
            new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Sinan")
        };
    }

    public static User[] findAll() {
        return users;
    }

    public User findById(UUID userId) {
        for (User user: users) {
            if (user.getId().equals(userId))
                return user;
        }
        return null;
    }
}
