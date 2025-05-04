package com.side.infrastructure.jpa.entity;

import com.side.infrastructure.jpa.common.MetadataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "role")
@DynamicInsert
@DynamicUpdate
public class RoleEntity {

    @Id
    private String roleId;

    @Comment("역할 명")
    private String roleName;

    @Comment("설명")
    private String description;

    @Embedded
    private MetadataEntity metadata;

    @Transient
    private List<UserRoleEntity> userRoles;
}
