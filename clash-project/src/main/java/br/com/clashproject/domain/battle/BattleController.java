package br.com.clashproject.domain.battle;

// package: com.clashroyale.controller

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/battles")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @GetMapping
    public ResponseEntity<List<Battle>> getAll() {
        return ResponseEntity.ok(battleService.getAllBattles());
    }

    @PostMapping
    public ResponseEntity<Battle> save(@RequestBody Battle battle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(battleService.saveBattle(battle));
    }
}