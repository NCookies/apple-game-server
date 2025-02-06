package xyz.ncookie.applegame.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import xyz.ncookie.applegame.dto.PlayerSessionDto;
import xyz.ncookie.applegame.dto.request.LeaveRoomRequest;
import xyz.ncookie.applegame.service.GameRoomService;
import xyz.ncookie.applegame.service.PlayerSessionService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final GameRoomService gameRoomService;
    private final PlayerSessionService playerSessionService;

    private final SimpMessagingTemplate messagingTemplate;

    // 브라우저가 새로고침 되거나 종료될 경우 클라이언트는 정상적으로 room leave 메세지를 보내지 못한다.
    // 때문에 서버에서 유저의 disconnected를 감지하여 퇴장 처리를 한다.
    @EventListener
    public void handleWebSocketDisconnectedListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId(); // 세션 ID 가져오기

        if (sessionId != null) {
            log.info("[WebSocket] 세션 종료 감지: " + sessionId);

            // 세션 ID를 이용해 플레이어 찾기
            PlayerSessionDto dto = playerSessionService.getPlayerSessionById(sessionId);
            if (dto != null) {
                log.info("[GAME] 플레이어 자동 퇴장 처리: {}", dto.playerId());
                gameRoomService.leaveRoom(LeaveRoomRequest.of(dto.roomId(), dto.playerId()));
                playerSessionService.removePlayerSessionById(sessionId);

                // 방 인원 업데이트 (WebSocket 브로드캐스트)
                if (dto.roomId() != null) {
                    messagingTemplate.convertAndSend(
                            "/topic/waitingRoom/" + dto.roomId() + "/player/update",
                            gameRoomService.getPlayerListInRoom(dto.roomId()));
                }
            }
        }
    }

}
