package com.ssafy.cnnect.user.entity;

import com.ssafy.cnnect.history.entity.History;
import com.ssafy.cnnect.voca.entity.Voca;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_level")
    private int userLevel;

    @OneToMany(mappedBy = "user")
    private List<UserBadge> userBadgeList;

    @OneToMany(mappedBy = "user")
    private List<History> userHistoryList;

    @OneToMany(mappedBy = "user")
    private List<Voca> userVocaList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
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
