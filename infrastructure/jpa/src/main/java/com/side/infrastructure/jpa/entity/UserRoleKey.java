package com.side.infrastructure.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class UserRoleKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -2403286936966239148L;

    private String userId;
    private String roleId;
}
