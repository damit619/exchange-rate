package com.exchangerate.security;

import com.exchangerate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userService.findUserByUserName(userName)
                .map(UserDetailsImpl::build)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
    }
}
