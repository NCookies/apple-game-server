package xyz.ncookie.applegame.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import xyz.ncookie.applegame.data.websocket.GameRoomMessage;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GameRoomWebSocketController {

    private List<Integer> apples;

    private List<Integer> generateApples() {
        List<Integer> newApples;
        int total;
        Random random = new Random();
        do {
            newApples = IntStream.range(0, 20 * 15)
                    .map(i -> random.nextInt(9) + 1)
                    .boxed()
                    .collect(Collectors.toList());
            total = newApples.stream().mapToInt(Integer::intValue).sum();
        } while (total % 10 != 0);
        return newApples;
    }

    @MessageMapping("/game/start")
    @SendTo("/topic/gameState")
    public Map<String, List<Integer>> startGame() {
        apples = generateApples();
        return Map.of("apples", apples);
    }

    @MessageMapping("/apple/remove")
    @SendTo("/topic/appleUpdate")
    public Map<String, List<Integer>> removeApples(Map<String, List<Integer>> payload) {
        List<Integer> removedIndices = payload.get("removedIndices");
        removedIndices.forEach(index -> apples.set(index, 0));
        return Map.of("removedIndices", removedIndices);
    }

}
