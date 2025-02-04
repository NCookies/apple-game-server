package xyz.ncookie.applegame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.applegame.dto.GameRoomDto;
import xyz.ncookie.applegame.dto.request.GameRoomRequest;
import xyz.ncookie.applegame.dto.request.JoinRoomRequest;
import xyz.ncookie.applegame.dto.request.LeaveRoomRequest;
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

        messagingTemplate.convertAndSend("/topic/rooms/update", response);
        return response;
    }

    // 방 입장
    @PostMapping("/join")
    public GameRoomInfoResponse joinGameRoom(@RequestBody JoinRoomRequest joinRoomRequest) {
        GameRoomDto dto = lobbyService.joinRoom(joinRoomRequest);

        // 방 입장 실패 시 false 반환
        if (dto == null) {
            return null;
        }

        GameRoomInfoResponse gameRoomInfoResponse = GameRoomInfoResponse.from(dto);
        messagingTemplate.convertAndSend("/topic/rooms/update", gameRoomInfoResponse);    // 방 정보 갱신
        messagingTemplate.convertAndSend("/topic/player/update", dto.players());         // 플레이어 업데이트

        return gameRoomInfoResponse;
    }
    
    // 방 퇴장
    @PostMapping("/leave")
    public boolean leaveGameRoom(@RequestBody LeaveRoomRequest leaveRoomRequest) {
        GameRoomDto dto = lobbyService.leaveRoom(leaveRoomRequest);

        // 퇴장하려는 방이 존재하지 않음
        if (dto == null) {
            return false;
        }

        // 방에 더 이상 유저가 없으면 삭제 처리
        if (dto.players().isEmpty()) {
            messagingTemplate.convertAndSend("/topic/rooms/delete", GameRoomInfoResponse.from(dto));
        } else {
            messagingTemplate.convertAndSend("/topic/rooms/update", GameRoomInfoResponse.from(dto));
        }
        
        // 정상적으로 방 퇴장 완료
        return true;
    }

}
