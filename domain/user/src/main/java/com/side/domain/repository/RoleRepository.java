package com.side.domain.repository;

import com.side.domain.model.Role;
import com.side.domain.model.User;

import java.util.Collection;

public interface RoleRepository {

    void create(User user);

    Role findById(String roleId);

    Collection<Role> loadRoleByUserId(String userId);
}

