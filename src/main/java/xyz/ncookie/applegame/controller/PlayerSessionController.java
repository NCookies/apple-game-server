package xyz.ncookie.applegame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.ncookie.applegame.dto.request.SavePlayerSessionRequest;
import xyz.ncookie.applegame.service.PlayerSessionService;

@Controller
@RequiredArgsConstructor
public class PlayerSessionController {

    private final PlayerSessionService playerSessionService;

    @MessageMapping("/waitingRoom/{roomId}/player/join")
    public void savePlayerSession(@RequestBody SavePlayerSessionRequest request,
                                     @DestinationVariable String roomId,
                                     SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        playerSessionService.savePlayerSession(request, sessionId, roomId);
    }

}
