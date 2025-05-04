package com.side.infrastructure.jpa.entity;

import com.side.domain.enums.UserStatus;
import com.side.domain.enums.UserType;
import com.side.infrastructure.jpa.common.MetadataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class UserEntity {

    @Id
    private String userId;

    @Comment("이름")
    private String userName;

    @Comment("비밀번호")
    private String password;

    @Comment("상태")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Comment("타입")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Comment("이메일")
    private String email;

    @Comment("핸드폰번호")
    private String mobile;

    @Comment("로그인실패횟수")
    private String failureCount;

    @Comment("잠금여부")
    private Boolean locked;

    @Comment("잠금시간")
    private Instant lockedAt;

    @Comment("잠금해제시간")
    private Instant unlockedAt;

    @Comment("설명")
    @Lob
    private String description;

    @Embedded
    private MetadataEntity metadata;

    @Transient
    private List<UserRoleEntity> userRoles;
}
