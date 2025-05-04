package com.side.domain.model;

public record RoleHierarchyInfo(String childId, String parentId, Role childRole, Role parentRole) {
}
