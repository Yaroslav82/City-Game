package com.game.cities.presentation.controller;

import com.game.cities.presentation.dto.GameSessionDTO;
import com.game.cities.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/begin")
    public ResponseEntity<GameSessionDTO> startGame() {
        GameSessionDTO session = gameService.startGame();
        return ResponseEntity.ok(session);
    }

    @GetMapping("/next")
    public ResponseEntity<GameSessionDTO> nextMove(@RequestParam String word, @RequestHeader("Session-Id") Long sessionId) {
        GameSessionDTO responseCity = gameService.nextCity(word, sessionId);
        return ResponseEntity.ok(responseCity);
    }

    @PostMapping("/end")
    public ResponseEntity<Void> endGame(@RequestHeader("Session-Id") Long sessionId) {
        gameService.endGame(sessionId);
        return ResponseEntity.ok().build();
    }
}
