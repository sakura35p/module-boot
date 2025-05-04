package com.side.security.service;

import com.side.domain.enums.UserStatus;
import com.side.domain.model.Role;
import com.side.domain.model.User;
import com.side.domain.service.RoleService;
import com.side.domain.service.UserService;
import com.side.security.jwt.dto.SecurityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final UserService userService;
    private final RoleService userRoleService;

    @Override
    public SecurityDto loadUserByUsername(String id) throws UsernameNotFoundException {

        User user = userService.loadUserByUserId(id, UserStatus.ACTIVE);
        Collection<Role> roles = userRoleService.loadRoleByUserId(id);

        return SecurityDto.builder()
                          .userId(user.id())
                          .password(user.password())
                          .authorities(roles.stream()
                                            .map(role -> new SimpleGrantedAuthority(role.roleId()))
                                            .toList())
                          .build();
    }


    public boolean isActive(String userId) {
        User user = userService.findById(userId);
        return UserStatus.ACTIVE == user.status();
    }

    public Collection<String> getUserRoles(String id) {
        return userRoleService.loadRoleByUserId(id).stream()
                              .map(Role::roleId)
                              .toList();
    }

}
