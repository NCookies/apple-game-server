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
        if (players.size() < maxPlayers) {
            players.add(playerId);
            return true;
        }

        return false;
    }

}
