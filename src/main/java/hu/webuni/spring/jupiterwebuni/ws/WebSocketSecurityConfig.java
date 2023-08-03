package hu.webuni.spring.jupiterwebuni.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//      deprecated de ahhoz hogy ugyanarrol az url-rol johessen a websocket keres, ez egy csrf vedelem, vagy az uj modon, xml-ben
//    a feliratkozasnal fogjuk meg a folyamatot nem az uzenet kuldesnel, feliratkozas egyszer tortenik, uzenetkuldes sokszor
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

        messages.simpSubscribeDestMatchers("/topic/courseChat/{courseId}")
//                fel van e iratkozva
//                ez egy metodus lesz, altalunk megirt bean, csak olyan kurzusra tudunk feliratkozni, aminek tagjai vagyunk
                .access("@courseChatGuard.checkCourseId(authentication, #courseId)");
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
