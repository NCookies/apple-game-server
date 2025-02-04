package xyz.ncookie.applegame.dto.request;

public record GameRoomRequest(
        String roomName,
        String userId,
        int maxPlayers
) {
}
