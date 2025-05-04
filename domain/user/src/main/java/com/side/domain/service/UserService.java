package com.side.domain.service;

import com.side.domain.enums.UserStatus;
import com.side.domain.model.User;
import com.side.domain.repository.UserRepositoryManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User findById(String id) {
        return UserRepositoryManager.getDefaultUserRepository().findById(id);
    }


    public User loadUserByUserId(String id, UserStatus status) {
        return UserRepositoryManager.getDefaultUserRepository().findByIdAndStatus(id, status);
    }
}

