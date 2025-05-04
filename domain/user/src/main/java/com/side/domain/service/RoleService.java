package com.side.domain.service;

import com.side.domain.model.Role;
import com.side.domain.repository.RoleRepositoryManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {

    public Role findById(String roleId) {
        return RoleRepositoryManager.getDefaultRoleRepository().findById(roleId);
    }

    public Collection<Role> loadRoleByUserId(String userId) {
        return RoleRepositoryManager.getDefaultRoleRepository().loadRoleByUserId(userId);
    }
}