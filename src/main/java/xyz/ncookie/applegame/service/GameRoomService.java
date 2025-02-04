package xyz.ncookie.applegame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.applegame.dto.GameRoomDto;
import xyz.ncookie.applegame.dto.request.GameRoomRequest;
import xyz.ncookie.applegame.dto.request.JoinRoomRequest;
import xyz.ncookie.applegame.dto.request.LeaveRoomRequest;
import xyz.ncookie.applegame.entity.GameRoom;
import xyz.ncookie.applegame.repository.GameRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;

    // 방 생성
    public GameRoomDto createRoom(GameRoomRequest roomRequest) {
        GameRoom room = GameRoom.builder()
                .roomName(roomRequest.roomName())
                .hostUserId(roomRequest.userId())
                .players(List.of(roomRequest.userId()))
                .maxPlayers(roomRequest.maxPlayers())
                .build();
        GameRoom entity = gameRoomRepository.save(room);
        return GameRoomDto.of(entity.getId(), entity.getRoomName(), entity.getHostUserId(), entity.getPlayers(), entity.getMaxPlayers());
    }

    // 모든 방 조회
    @Transactional(readOnly = true)
    public List<GameRoomDto> getRoomList() {
        List<GameRoomDto> dtoList = new ArrayList<>();

        for (GameRoom entity : gameRoomRepository.findAll()) {
            dtoList.add(GameRoomDto.of(
                    entity.getId(),
                    entity.getRoomName(),
                    entity.getHostUserId(),
                    entity.getPlayers(),
                    entity.getMaxPlayers()));
        }

        return dtoList;
    }

    // 방 입장
    public GameRoomDto joinRoom(JoinRoomRequest joinRoomRequest) {
        Optional<GameRoom> optional = gameRoomRepository.findById(joinRoomRequest.roomId());
        if (optional.isPresent()) {
            GameRoom room = optional.get();
            if (room.joinRoom(joinRoomRequest.userId())) {
                gameRoomRepository.save(room);
                return GameRoomDto.of(room.getId(),
                        room.getRoomName(),
                        room.getHostUserId(),
                        room.getPlayers(),
                        room.getMaxPlayers());
            }
        }

        return null;    // TODO: 방이 존재하지 않을 때 예외처리 해줘야 함
    }

    // 방 퇴장
    public GameRoomDto leaveRoom(LeaveRoomRequest leaveRoomRequest) {
        Optional<GameRoom> optional = gameRoomRepository.findById(leaveRoomRequest.roomId());

        if (optional.isPresent()) {
            GameRoom room = optional.get();
            room.leaveRoom(leaveRoomRequest.userId());

            // 방에 더 이상 유저가 없으면 방 삭제
            if (room.getPlayers().isEmpty()) {
                deleteRoom(leaveRoomRequest.roomId());
            } else {  // 방이 삭제되지 않을 때만 정보 갱신
                gameRoomRepository.save(room);
            }

            return GameRoomDto.of(room.getId(),
                    room.getRoomName(),
                    room.getHostUserId(),
                    room.getPlayers(),
                    room.getMaxPlayers());
        }

        return null;
    }

    // 방 삭제
    public void deleteRoom(String roomId) {
        gameRoomRepository.deleteById(roomId);
    }

}
