package xyz.ncookie.applegame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;

    // 초기 접속 시 방 목록 조회
    @GetMapping
    public List<GameRoomInfoResponse> getRoomList() {
        return lobbyService.getRoomList().stream()
                .map(GameRoomInfoResponse::from)
                .toList();
    }

    // 방 생성
    @PostMapping("/create")
    public GameRoomInfoResponse createGameRoom(@RequestBody GameRoomRequest gameRoomRequest) {
        GameRoomDto dto = lobbyService.createRoom(gameRoomRequest);
        GameRoomInfoResponse response = GameRoomInfoResponse.from(dto);

        messagingTemplate.convertAndSend("/topic/rooms", response);
        return response;
    }

    // 방 입장
    @PostMapping("/join")
    public boolean joinGameRoom(@RequestBody JoinRoomRequest joinRoomRequest) {
        GameRoomDto dto = lobbyService.joinRoom(joinRoomRequest);

        // 방 입장 실패 시 false 반환
        if (dto == null) {
            return false;
        }

        messagingTemplate.convertAndSend("/topic/rooms", GameRoomInfoResponse.from(dto));
        return true;
    }

}
