package xyz.ncookie.applegame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.applegame.entity.GameRoom;
import xyz.ncookie.applegame.repository.GameRoomRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;

    // 방 생성
    public GameRoom createRoom(String name, int maxPlayers) {
        GameRoom room = GameRoom.of(name, maxPlayers);
        return gameRoomRepository.save(room);
    }

    // 모든 방 조회
    @Transactional(readOnly = true)
    public List<GameRoom> getAllRooms() {
        return gameRoomRepository.findAll();
    }

    // 특정 방 조회
    @Transactional(readOnly = true)
    public GameRoom getRoomById(String id) {
        return gameRoomRepository.findById(id).orElse(null);
    }

}
