package xyz.ncookie.applegame.dto.response;

import xyz.ncookie.applegame.dto.GameRoomDto;

public record GameRoomInfoResponse(
        String roomId,
        String roomName,
        String hostUserId,
        int currentPlayers,
        int maxPlayers,
        boolean isJoinable
) {

    public static GameRoomInfoResponse from(GameRoomDto dto) {
        int currentPlayers = dto.players().size();
        int maxPlayers = dto.maxPlayers();
        return new GameRoomInfoResponse(
                dto.roomId(),
                dto.roomName(),
                dto.hostUserId(),
                currentPlayers,
                maxPlayers,
                maxPlayers - currentPlayers > 0
        );
    }

}
