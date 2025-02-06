package xyz.ncookie.applegame.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.ncookie.applegame.entity.PlayerSession;

public interface PlayerSessionRepository extends CrudRepository<PlayerSession, String> {
}
