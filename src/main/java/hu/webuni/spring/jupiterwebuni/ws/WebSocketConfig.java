package hu.webuni.spring.jupiterwebuni.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        milyen url-en mukodjon a stomp
        registry.addEndpoint("/api/stomp");
//        a sockjs sima pollozással tudja emulalni a websocketes mukodest, frontenden be kell kapcsolni a websocketet
//        hogy websocketkent proxyzni
        registry.addEndpoint("/api/stomp").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//  feliratkoznak a /topic/courseChat-re
//        mivel csak /topic barmit irnak utana arra iratkoznak fel
        registry.enableSimpleBroker("/topic");
//        ha uzenetet akarnak kuldeni azt pedig a /app/chatre fogjak kuldeni
        registry.setApplicationDestinationPrefixes("/app");
    }


//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                if(StompCommand.CONNECT.equals(accessor.getCommand())) {
//
//                    List<String> authHeaders = accessor.getNativeHeader("X-Authorization");
//
//                    UsernamePasswordAuthenticationToken authentication = JwtAuthFilter.createUserDetailsFromAuthHeader(authHeaders.get(0), jwtService);
//                    accessor.setUser(authentication);
//                }
//
//                return message;
//            }
//
//        });
//    }
}
