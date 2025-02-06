package xyz.ncookie.applegame.dto;

import xyz.ncookie.applegame.entity.PlayerSession;

public record PlayerSessionDto(
        String sessionId,
        String playerId,
        String roomId
) {

    public static PlayerSessionDto from(PlayerSession playerSession) {
        return new PlayerSessionDto(playerSession.getSessionId(),
                playerSession.getPlayerId(),
                playerSession.getRoomId());
    }

}
