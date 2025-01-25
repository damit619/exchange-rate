package com.exchangerate.service;

import com.exchangerate.modle.user.User;
import com.exchangerate.service.user.UserService;
import com.exchangerate.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserServiceTest {

    @Test
    public void testFindUserByUserName () {
        UserService userService = new UserServiceImpl();

        Optional<User> user = userService.findUserByUserName("user1");

        assertThat("user should be present", user.isPresent());
        assertThat("username should be user1", user.map(User::userName).orElse(null), equalTo("user1"));
    }
}
