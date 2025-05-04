package com.side.infrastructure.valkey.config;

import com.side.infrastructure.valkey.properties.RedisProperties;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.internal.HostAndPort;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DnsResolvers;
import io.lettuce.core.resource.MappingSocketAddressResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

    private static final String LOCALHOST = "localhost";

    private final Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {

        // Redis 클러스터 설정 생성
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster()
                                                                                                      .getNodes());
        clusterConfiguration.setMaxRedirects(redisProperties.getMaxRedirects());
        clusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));

        // 클러스터 토폴로지 새로고침 옵션 설정
        // 토폴로지: Redis 클러스터에서 각 노드들의 구성과 연결 상태를 나타내는 전체적인 구조
        // - 마스터/슬레이브 노드의 관계
        // - 각 노드의 상태(활성/비활성)
        // - 슬롯 할당 정보
        // - 노드간 연결 정보
        // 토폴로지 새로고침: 변경된 클러스터 구조를 감지하고 클라이언트의 정보를 최신 상태로 업데이트
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                                                                                            .enableAllAdaptiveRefreshTriggers() // 모든 적응형 새로고침 트리거 활성화
                                                                                            .enablePeriodicRefresh(Duration.ofHours(1L)) // 주기적으로 1시간마다 새로고침
                                                                                            .dynamicRefreshSources(true) // 동적 새로고침 소스 활성화
                                                                                            .build();

        // 소켓 연결 옵션 설정
        SocketOptions socketOptions = SocketOptions.builder()
                                                   .connectTimeout(Duration.ofMillis(100L)) // 연결 타임아웃 100ms
                                                   .keepAlive(true) // TCP keepalive 활성화
                                                   .build();

        // Redis 클라이언트 옵션 설정
        ClientOptions options = ClusterClientOptions.builder()
                                                    .autoReconnect(true) // 자동 재연결 활성화
                                                    .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS) // 연결 해제시 명령어 거부
                                                    .topologyRefreshOptions(topologyRefreshOptions) // 토폴로지 새로고침 옵션 적용
                                                    .socketOptions(socketOptions) // 소켓 옵션 적용
                                                    .build();

        // Lettuce 클라이언트 설정 빌더 생성
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder()
                                                                                                         .clientOptions(options)
                                                                                                         .commandTimeout(Duration.ofMillis(redisProperties.getTimeout()));

        // 로컬 환경 여부 확인
        boolean isLocal = env.acceptsProfiles(Profiles.of("local"));

        // 로컬 환경일 경우 DNS 리졸버 설정
        if (isLocal) {
            // 커스텀 소켓 주소 리졸버 생성
            MappingSocketAddressResolver resolver = MappingSocketAddressResolver.create(
                    DnsResolvers.UNRESOLVED,
                    hostAndPort -> HostAndPort.of(LOCALHOST, hostAndPort.getPort())
            );

            // 클라이언트 리소스에 리졸버 설정
            ClientResources clientResources = ClientResources.builder()
                                                             .socketAddressResolver(resolver)
                                                             .build();

            builder.clientResources(clientResources);
        }

        // Lettuce 연결 팩토리 생성 및 설정
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(clusterConfiguration, builder.build());
        connectionFactory.setValidateConnection(true); // 연결 유효성 검사 활성화

        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }


}
