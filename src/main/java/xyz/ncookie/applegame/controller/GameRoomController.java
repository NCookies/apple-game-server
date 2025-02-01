package xyz.ncookie.applegame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.ncookie.applegame.entity.GameRoom;
import xyz.ncookie.applegame.service.GameRoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class GameRoomController {

    private final GameRoomService gameRoomService;

    // 방 생성 API
    @PostMapping("/create")
    public GameRoom createRoom(@RequestParam String name, @RequestParam(value = "max_players") int maxPlayers) {
        return gameRoomService.createRoom(name, maxPlayers);
    }

    // 방 목록 조회 API
    @GetMapping
    public List<GameRoom> getAllRooms() {
        return gameRoomService.getAllRooms();
    }

    // 특정 방 조회 API
    @GetMapping("/{id}")
    public GameRoom getRoom(@PathVariable String id) {
        return gameRoomService.getRoomById(id);
    }

}
