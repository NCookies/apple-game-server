package xyz.ncookie.applegame.dto;

import java.util.List;

public record GameRoomDto(
        String roomId,
        String roomName,
        String hostUserId,
        List<String> players,
        int maxPlayers
) {

    public static GameRoomDto of(String roomId,
                                 String roomName,
                                 String hostUserId,
                                 List<String> players,
                                 int maxPlayers) {
        return new GameRoomDto(roomId, roomName, hostUserId, players, maxPlayers);
    }

}
