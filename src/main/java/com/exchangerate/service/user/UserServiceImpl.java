package com.exchangerate.service.user;

import com.exchangerate.modle.user.User;
import com.exchangerate.security.UserDB;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Map<String, User> USER_DB = UserDB.getUserDb();

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return USER_DB.values().stream()
                .filter(user -> user.userName().equalsIgnoreCase(userName))
                .findFirst();
    }
}
