package xyz.ncookie.applegame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.applegame.dto.GameRoomDto;
import xyz.ncookie.applegame.dto.request.GameRoomRequest;
import xyz.ncookie.applegame.dto.request.JoinRoomRequest;
import xyz.ncookie.applegame.dto.response.GameRoomInfoResponse;
import xyz.ncookie.applegame.service.LobbyService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class LobbyController {

    private final LobbyService lobbyService;

    // 초기 접속 시 방 목록 조회
    @GetMapping
    public List<GameRoomInfoResponse> getRoomList() {
        return lobbyService.getRoomList().stream()
                .map(GameRoomInfoResponse::from)
                .toList();
    }

    // 방 생성
    @MessageMapping("/rooms/create")
    @SendTo("/topic/rooms")
    public GameRoomInfoResponse createRoom(@Payload GameRoomRequest roomRequest) {
        GameRoomDto dto = lobbyService.createRoom(roomRequest);
        return GameRoomInfoResponse.from(dto);
    }

    // 방 입장
    @MessageMapping("/rooms/join")
    @SendTo("/topic/rooms")
    public GameRoomInfoResponse joinRoom(@Payload JoinRoomRequest joinRoomRequest) {
        GameRoomDto dto = lobbyService.joinRoom(joinRoomRequest);
        return GameRoomInfoResponse.from(dto);
    }

}
