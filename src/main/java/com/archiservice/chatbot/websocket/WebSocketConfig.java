package com.archiservice.chatbot.websocket;

import com.archiservice.chatbot.domain.AuthInfo;
import com.archiservice.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.unit.DataSize;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final int HEARTBEAT_INTERVAL_MILLIS = (int) TimeUnit.SECONDS.toMillis(10);
    private static final int MESSAGE_SIZE_LIMIT_BYTES = (int) DataSize.ofKilobytes(64).toBytes();
    private static final int SEND_TIME_LIMIT_MILLIS = (int) TimeUnit.SECONDS.toMillis(20);
    private static final int SEND_BUFFER_SIZE_LIMIT_BYTES = (int) DataSize.ofKilobytes(512).toBytes();

    private final JwtUtil jwtTokenProvider;

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request,
                WebSocketHandler wsHandler,
                Map<String, Object> attributes) {
                String token = extractTokenFromRequest(request);
                if (token != null && jwtTokenProvider.validateToken(token)) {

                    // TODO: 토큰에서 userId, tagCode, ageCode 파싱(ex : String userId = jwtTokenProvider.getUserId(token))
                    Long userId = 1L;
                    int tagCode = 42;
                    int ageCode = 3;

                    return AuthInfo.of(userId, tagCode, ageCode);
                }
                return null;
            }
        };
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        String query = request.getURI().getQuery();
        if(query!=null){
            return parseTokenFromQuery(query);
        }
        
        return null;
    }

    private String parseTokenFromQuery(String query) {
        String[] params = query.split("&");
        for (String param : params) {
            if (param.startsWith("token=")) {
                return param.substring(6);
            }
        }
        return null;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(handshakeHandler())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue")
                .setTaskScheduler(heartbeatScheduler())
                .setHeartbeatValue(new long[]{HEARTBEAT_INTERVAL_MILLIS, HEARTBEAT_INTERVAL_MILLIS});
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(MESSAGE_SIZE_LIMIT_BYTES)
                .setSendTimeLimit(SEND_TIME_LIMIT_MILLIS)
                .setSendBufferSizeLimit(SEND_BUFFER_SIZE_LIMIT_BYTES);
    }

    private TaskScheduler heartbeatScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("ws-heartbeat-");
        scheduler.setDaemon(true);
        scheduler.initialize();
        return scheduler;
    }
}
