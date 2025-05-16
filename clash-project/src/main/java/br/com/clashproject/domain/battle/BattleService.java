package br.com.clashproject.domain.battle;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

    public List<Battle> getAllBattles() {
        return battleRepository.findAll();
    }

    public Battle saveBattle(Battle battle) {
        return battleRepository.save(battle);
    }
}
