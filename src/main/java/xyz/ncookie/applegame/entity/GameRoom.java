package xyz.ncookie.applegame.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_room")
public class GameRoom {

    @Id
    private String id; // 방 ID (UUID)

    @Column(nullable = false)
    private String name; // 방 이름

    @Column(nullable = false)
    private int maxPlayers; // 최대 인원

    public static GameRoom of(String name, int maxPlayers) {
        return new GameRoom(UUID.randomUUID().toString(), name, maxPlayers);
    }

}
