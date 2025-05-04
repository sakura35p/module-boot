package com.side.infrastructure.jpa.mapper;


import com.side.domain.model.RoleHierarchyInfo;
import com.side.infrastructure.jpa.entity.RoleHierarchyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleHierarchyMapper {

    RoleHierarchyMapper RoleHierarchyMapper = Mappers.getMapper(RoleHierarchyMapper.class);

    RoleHierarchyInfo toDomain(RoleHierarchyEntity entity);

    RoleHierarchyEntity toEntity(RoleHierarchyInfo roleHierarchyInfo);
}
