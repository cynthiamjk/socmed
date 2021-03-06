package com.cynthia.socmed.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SocmedUserDetails implements UserDetails {

        private User user;

    public SocmedUserDetails(User user) {

            this.user = user;

        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Role role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

        public int getId() {
            return user.getId();

        }




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

        public User getUserDetails() {
            return user;

        }
}
