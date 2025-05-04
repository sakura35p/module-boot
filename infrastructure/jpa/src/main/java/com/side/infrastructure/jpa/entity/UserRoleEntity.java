package com.side.infrastructure.jpa.entity;

import com.side.infrastructure.jpa.common.MetadataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "user_role")
@DynamicInsert
@DynamicUpdate
@IdClass(UserRoleKey.class)
public class UserRoleEntity {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Embedded
    private MetadataEntity metadata;
}
