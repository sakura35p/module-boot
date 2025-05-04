package com.side.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.security.jwt.claims.JwtClaims;
import com.side.security.jwt.config.AuthenticationDetailsSource;
import com.side.security.jwt.config.JwtProperties;
import com.side.security.jwt.dto.SecurityDto;
import com.side.security.jwt.enums.JwtTokenType;
import com.side.security.jwt.service.JwtService;
import com.side.security.service.RoleHierarchyService;
import com.side.security.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtProperties jwtProperties;
    private final SecurityService securityService;
    private final JwtService jwtService;
    private final RoleHierarchyService roleHierarchyService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException {

        log.info("## JwtAuthenticationFilter doFilterInternal ##");
        final String token = request.getHeader(AUTHORIZATION_HEADER);

        try {


            if (!StringUtils.hasText(token)) {
                filterChain.doFilter(request, response);
                return;
            }


            JwtClaims jwtClaims = jwtService.getJwtClaims(token);
            String userId = jwtClaims.getUserId();
            Collection<String> roles = jwtClaims.getRoles();
            JwtTokenType tokenType = jwtClaims.getTokenType();

            if (!JwtTokenType.ACCESS.equals(tokenType)) {
                log.error("[Not Access Token] token : {}, userId : {}, tokenType : {}", token, userId, tokenType);
                filterChain.doFilter(request, response);
                return;
            }

            boolean isActive = securityService.isActive(userId);

            if (!isActive) {
                log.error("[Not ACTIVE USER] token : {}, userId : {}", token, userId);
                filterChain.doFilter(request, response);
                return;
            }

            Collection<String> userRoleList = securityService.getUserRoles(userId);

            Set<String> rolesSet = new HashSet<>(roles);
            Set<String> userRoleListSet = new HashSet<>(userRoleList);
            Set<String> roleSetCopy = new HashSet<>(rolesSet);

            roleSetCopy.removeAll(userRoleList);
            userRoleListSet.removeAll(rolesSet);

            if (!roleSetCopy.isEmpty() || !userRoleListSet.isEmpty()) {
                log.error("[Invalid Token Roles] token : {}, userId : {}, role : {}, userRole : {}", token, userId, roles, userRoleList);
                filterChain.doFilter(request, response);
                return;
            }


            Collection<GrantedAuthority> authorities = roleHierarchyService.getGrantedAuthorities(roles);

            log.info("authorities : {}", authorities);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    SecurityDto.builder()
                               .userId(userId)
                               .authorities(authorities)
                               .build(),
                    null,
                    authorities
            );

            authentication.setDetails(new AuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            failMessageReturn(response);
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(jwtProperties.getExcludePath()).anyMatch(request.getRequestURI()::startsWith);
    }

    private void failMessageReturn(ServletResponse response) throws IOException {

        try (OutputStream os = response.getOutputStream()) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            os.write(
                    objectMapper.writeValueAsString(Map.of("result", false))
                                .getBytes()
            );
            os.flush();
        }
    }
}
