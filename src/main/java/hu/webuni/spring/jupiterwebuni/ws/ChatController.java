package hu.webuni.spring.jupiterwebuni.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

//    o fogadja a websocketen beerkezo uzeneteket, majd szetbroadcastolja a topicra es aki feliratkozott az megkapja
    @MessageMapping("/chat")
    @PreAuthorize("#message.sender == authentication.principal.username")   // csak a sajat usere neveben lehessen kuldeni
    public void onMessage(ChatMessage message) {    // a message.sender a sender a Chatmessage egy valtozoja
        simpMessagingTemplate.convertAndSend(
//                bongeszobe pl angular-nal igy erem el: localhost:4200/coursechat/123
                "/topic/courseChat/" + message.getCourseId(),
                String.format("%s: %s", message.getSender(), message.getText()));
    }
}
