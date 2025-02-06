package xyz.ncookie.applegame.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.applegame.dto.PlayerSessionDto;
import xyz.ncookie.applegame.dto.request.SavePlayerSessionRequest;
import xyz.ncookie.applegame.entity.PlayerSession;
import xyz.ncookie.applegame.repository.PlayerSessionRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlayerSessionService {

    private final PlayerSessionRepository playerSessionRepository;

    public void savePlayerSession(SavePlayerSessionRequest request,
                                  String sessionId,
                                  String roomId) {
        log.info("플레이어 세션 저장: {}", request.playerId());
        playerSessionRepository.save(
                PlayerSession.builder()
                        .sessionId(sessionId)
                        .playerId(request.playerId())
                        .roomId(roomId)
                        .build()
        );
    }

    public PlayerSessionDto getPlayerSessionById(String sessionId) {
        return playerSessionRepository.findById(sessionId)
                .map(PlayerSessionDto::from)
                .orElse(null);
    }

    public void removePlayerSessionById(String sessionId) {
        playerSessionRepository.deleteById(sessionId);
    }
}
