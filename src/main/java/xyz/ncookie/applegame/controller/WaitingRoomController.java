package xyz.ncookie.applegame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import xyz.ncookie.applegame.service.GameRoomService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WaitingRoomController {

    private final GameRoomService gameRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    // 대기실 플레이어 리스트 업데이트
    @MessageMapping("/waitingRoom/{roomId}/player/update")
    @SendTo("/topic/waitingRoom/{roomId}/player/update")
    public List<String> updatePlayerList(@DestinationVariable String roomId) {
        return gameRoomService.getPlayerListInRoom(roomId);
    }

}
