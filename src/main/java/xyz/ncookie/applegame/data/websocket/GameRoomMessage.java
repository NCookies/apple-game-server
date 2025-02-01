package xyz.ncookie.applegame.data.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRoomMessage {
    private String type; // "JOIN", "LEAVE"
    private String roomId;
    private String playerName;

    public static GameRoomMessage of(String type, String roomId, String playerName) {
        return new GameRoomMessage(type, roomId, playerName);
    }
}
