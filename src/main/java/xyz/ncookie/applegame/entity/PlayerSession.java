package xyz.ncookie.applegame.entity;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "player_session")
public class PlayerSession {

    @Id
    private String sessionId;   // WebSocket 세션 ID
    private String playerId;    // 유저 ID
    private String roomId;      // 유저가 속한 방 ID

}
