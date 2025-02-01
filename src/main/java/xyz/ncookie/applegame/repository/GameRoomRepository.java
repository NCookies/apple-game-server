package xyz.ncookie.applegame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.ncookie.applegame.entity.GameRoom;

public interface GameRoomRepository extends JpaRepository<GameRoom, String> {
}
