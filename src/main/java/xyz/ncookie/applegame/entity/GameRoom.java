package xyz.ncookie.applegame.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "game_room")
public class GameRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // 방 ID (UUID)

    @Column(nullable = false)
    private String roomName; // 방 이름
    
    private String hostUserId;  // 방장 유저 ID

    private List<String> players;

    @Column(nullable = false)
    private int maxPlayers; // 최대 인원

    public boolean joinRoom(String playerId) {
        // 최대 인원 꽉 차면 입장 불가
        if (players.size() >= maxPlayers) {
            return false;
        }

        // 호스트 방 생성 시 중복 입장 방지용
        if (players.contains(playerId)) {
            return true;
        }

        players.add(playerId);
        return true;
    }

    public void leaveRoom(String playerId) {
        players.remove(playerId);
    }

}
