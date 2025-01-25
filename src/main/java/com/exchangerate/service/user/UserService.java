package com.exchangerate.service.user;

import com.exchangerate.modle.user.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUserName(String userName);
}
