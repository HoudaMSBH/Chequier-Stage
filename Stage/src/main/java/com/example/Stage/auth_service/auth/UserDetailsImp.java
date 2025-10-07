package com.example.Stage.auth_service.auth;

import com.example.Stage.entity.Banquier;
import com.example.Stage.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImp implements UserDetails {

    private Long id;
    private String username;

    @JsonIgnore
    private String password; // null si LDAP configuré

    private Banquier banquier;

    public UserDetailsImp(Long id, String username, Banquier banquier, String password) {
        this.id = id;
        this.username = username;
        this.banquier = banquier;
        this.password = password;
    }

    public static UserDetailsImp build(User user) {
        return new UserDetailsImp(
                user.getId(),
                user.getUsername(),
                user.getBanquier(),
                user.getPassword()
        );
    }

    // ✅ Retourne une collection vide si tu n'as pas de rôles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
