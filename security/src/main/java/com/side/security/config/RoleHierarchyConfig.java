package com.side.security.config;

import com.side.domain.model.RoleHierarchyInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleHierarchyConfig {

    @Bean
    public static RoleHierarchy roleHierarchy(JdbcTemplate jdbcTemplate) {

        List<RoleHierarchyInfo> roleHierarchies = jdbcTemplate.query(
                "select lower_role, higher_role from role_hierarchy",
                (rs, rowNum) -> new RoleHierarchyInfo(
                        rs.getString("lower_role"),
                        rs.getString("higher_role"),
                        null,  // childRole
                        null   // parentRole
                )
        );

        return RoleHierarchyImpl.fromHierarchy(roleHierarchies.stream()
                                                              .map(hierarchy -> hierarchy.childId() + " > " + hierarchy.parentId())
                                                              .collect(Collectors.joining("\n")));
    }
}