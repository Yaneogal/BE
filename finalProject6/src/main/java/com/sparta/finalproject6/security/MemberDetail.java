package com.sparta.finalproject6.security;

import com.sparta.finalproject6.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class MemberDetail implements UserDetails {

    private final User user;

    public MemberDetail(User member) {
        this.user = member;
    }

    public User getMember() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return Collections.emptyList();}

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
