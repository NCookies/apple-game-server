package xyz.ncookie.applegame.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // subscribe
        registry.setApplicationDestinationPrefixes("/app"); // publish
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 로비와 인게임은 별도의 소켓 연결을 가진다.
        registry.addEndpoint("/ws/lobby").setAllowedOrigins("http://localhost:3000").withSockJS();
        registry.addEndpoint("/ws/game").setAllowedOrigins("http://localhost:3000").withSockJS();
    }

}
