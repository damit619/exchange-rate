package com.exchangerate.security;

import com.exchangerate.modle.user.User;
import com.exchangerate.modle.user.UserType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    @Getter
    private String uuid;
    private String userName;
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String uuid, String userName, String password, List<GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.userTypes().stream()
                .map(UserDetailsImpl::grantedAuthority)
                .toList();
        return new UserDetailsImpl(
                user.uuid(),
                user.userName(),
                user.password(),
                authorities);
    }

    private static GrantedAuthority grantedAuthority (UserType userType) {
        return userType::name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(uuid, user.getUuid());
    }
}
