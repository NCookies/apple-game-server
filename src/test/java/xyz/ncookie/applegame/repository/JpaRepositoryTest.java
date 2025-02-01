package xyz.ncookie.applegame.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import xyz.ncookie.applegame.entity.GameRoom;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@DataJpaTest
public class JpaRepositoryTest {

    @Autowired
    private GameRoomRepository gameRoomRepository;

    @Test
    @DisplayName("게임 방을 생성하고 저장하면 DB에서 조회할 수 있어야 한다")
    void givenGameRoomInfo_whenInserting_thenWorksFine() {
        // Given
        GameRoom room = GameRoom.of("Test Room", 8);
        gameRoomRepository.save(room);

        // When
        Optional<GameRoom> foundRoom = gameRoomRepository.findById(room.getId());

        // Then
        assertThat(foundRoom).isPresent();  // 값이 존재해야 함
        assertThat(foundRoom.get().getName()).isEqualTo("Test Room");
        assertThat(foundRoom.get().getMaxPlayers()).isEqualTo(8);
    }

    @Test
    @DisplayName("여러 개의 게임 방을 저장한 후 전체 조회할 수 있어야 한다")
    void findAllGameRooms() {
        // Given
        GameRoom room1 = GameRoom.of("Room 1", 5);
        GameRoom room2 = GameRoom.of("Room 2", 10);
        gameRoomRepository.save(room1);
        gameRoomRepository.save(room2);

        // When
        List<GameRoom> rooms = gameRoomRepository.findAll();

        // Then
        assertThat(rooms).hasSize(2);
    }

    @Test
    @DisplayName("게임 방을 삭제하면 더 이상 조회되지 않아야 한다")
    void deleteGameRoom() {
        // Given
        GameRoom room = GameRoom.of("To Be Deleted", 6);
        gameRoomRepository.save(room);

        // When
        gameRoomRepository.deleteById(room.getId());
        Optional<GameRoom> deletedRoom = gameRoomRepository.findById(room.getId());

        // Then
        assertThat(deletedRoom).isEmpty();  // 삭제된 후 조회되지 않아야 함
    }

}
