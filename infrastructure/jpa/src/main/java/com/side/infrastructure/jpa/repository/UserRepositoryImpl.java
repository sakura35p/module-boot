package com.side.infrastructure.jpa.repository;

import com.side.domain.enums.UserStatus;
import com.side.domain.model.User;
import com.side.domain.repository.UserRepository;
import com.side.domain.repository.UserRepositoryManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.side.domain.enums.RepositoryTypeEnum.JPA;
import static com.side.infrastructure.jpa.mapper.UserMapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;

    @PostConstruct
    public void init() {
        UserRepositoryManager.addUserRepository(JPA, this);
    }

    @Override
    public void create(User user) {
        repository.save(UserMapper.toEntity(user));
    }

    @Override
    public User findById(String userId) {
        return UserMapper.toDomain(repository.findById(userId)
                                             .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId)));
    }

    @Override
    public User findByIdAndStatus(String userId, UserStatus status) {
        return UserMapper.toDomain(repository.findByUserIdAndStatus(userId, status)
                                             .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId)));
    }
}
