package xyz.ncookie.applegame.dto.request;

public record LeaveRoomRequest(
        String roomId,
        String userId
) {
}
