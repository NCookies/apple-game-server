package xyz.ncookie.applegame.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.ncookie.applegame.entity.GameRoom;

public interface GameRoomRepository extends CrudRepository<GameRoom, String> {
}
