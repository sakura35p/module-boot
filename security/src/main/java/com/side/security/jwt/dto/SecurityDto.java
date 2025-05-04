package com.side.security.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

@Getter
@Builder
public class SecurityDto implements UserDetails {

    @Serial
    private static final long serialVersionUID = 8852543273010798857L;

    private String userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
}
