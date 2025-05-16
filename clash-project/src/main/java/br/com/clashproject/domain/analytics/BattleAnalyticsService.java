package br.com.clashproject.domain.analytics;

import br.com.clashproject.domain.battle.Battle;
import br.com.clashproject.domain.battle.BattleRepository;
import br.com.clashproject.domain.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BattleAnalyticsService {

    private final BattleRepository repository;

    public Map<String, Double> calculateWinLossPercentageByCard(String card, Instant start, Instant end) {
        List<Battle> battles = repository.findAll(); // substituir por query com intervalo

        long totalWins = 0, totalLosses = 0;

        for (Battle b : battles) {
            if (b.getTimestamp().isBefore(start) || b.getTimestamp().isAfter(end)) continue;

            boolean cardInP1 = b.getPlayer1().getDeck().contains(card);
            boolean cardInP2 = b.getPlayer2().getDeck().contains(card);

            if (cardInP1) {
                if (b.getWinner().equals("player1")) totalWins++;
                else totalLosses++;
            } else if (cardInP2) {
                if (b.getWinner().equals("player2")) totalWins++;
                else totalLosses++;
            }
        }

        long total = totalWins + totalLosses;
        if (total == 0) return Map.of("wins", 0.0, "losses", 0.0);

        double winPct = (totalWins * 100.0) / total;
        double lossPct = 100.0 - winPct;

        return Map.of("wins", winPct, "losses", lossPct);
    }

    public List<List<String>> findWinningDecksAboveThreshold(double threshold, Instant start, Instant end) {
        List<Battle> battles = repository.findAll(); // substituir por query com intervalo

        Map<List<String>, Integer> winCounts = new HashMap<>();
        Map<List<String>, Integer> totalCounts = new HashMap<>();

        for (Battle b : battles) {
            if (b.getTimestamp().isBefore(start) || b.getTimestamp().isAfter(end)) continue;

            List<String> winnerDeck;
            if (b.getWinner().equals("player1")) {
                winnerDeck = b.getPlayer1().getDeck();
            } else {
                winnerDeck = b.getPlayer2().getDeck();
            }

            totalCounts.put(winnerDeck, totalCounts.getOrDefault(winnerDeck, 0) + 1);
            winCounts.put(winnerDeck, winCounts.getOrDefault(winnerDeck, 0) + 1);
        }

        List<List<String>> result = new ArrayList<>();
        for (var entry : totalCounts.entrySet()) {
            List<String> deck = entry.getKey();
            int total = entry.getValue();
            int wins = winCounts.getOrDefault(deck, 0);

            double winRate = (wins * 100.0) / total;
            if (winRate > threshold) {
                result.add(deck);
            }
        }

        return result;
    }

    public long countDefeatsByCardCombo(List<String> combo, Instant start, Instant end) {
        List<Battle> battles = repository.findAll();
        long defeats = 0;

        for (Battle b : battles) {
            if (b.getTimestamp().isBefore(start) || b.getTimestamp().isAfter(end)) continue;

            Player loser = b.getWinner().equals("player1") ? b.getPlayer2() : b.getPlayer1();
            if (loser.getDeck().containsAll(combo)) {
                defeats++;
            }
        }

        return defeats;
    }

    public long countVictoriesBySpecialCondition(String card, double trophyDiffPercent, Instant start, Instant end) {
        List<Battle> battles = repository.findAll();
        long count = 0;

        for (Battle b : battles) {
            // Verifica se a batalha está dentro do intervalo de tempo
            if (b.getTimestamp().isBefore(start) || b.getTimestamp().isAfter(end)) continue;

            Player winner = b.getWinner().equals("player1") ? b.getPlayer1() : b.getPlayer2();
            Player loser = b.getWinner().equals("player1") ? b.getPlayer2() : b.getPlayer1();

            // Verifica se o vencedor possui a carta no deck
            if (!winner.getDeck().contains(card)) continue;

            // Garantir que a diferença de troféus seja sempre positiva e calculada corretamente
            double trophyDiff = Math.abs(winner.getTrophies() - loser.getTrophies());

            // Verifica se a diferença de troféus é positiva
            if (trophyDiff <= 0) continue;

            // Calcula o percentual de diferença de troféus
            double pct = (trophyDiff / loser.getTrophies()) * 100.0;

            // Verifica as condições de vitória especial
            if (pct >= trophyDiffPercent && loser.getTowersDestroyed() >= 2) {
                count++;
            }
        }

        return count;
    }
    public List<List<String>> listWinningCardCombos(int comboSize, double winRateThreshold, Instant start, Instant end) {
        List<Battle> battles = repository.findAll();
        Map<Set<String>, Integer> winCounts = new HashMap<>();
        Map<Set<String>, Integer> totalCounts = new HashMap<>();

        for (Battle b : battles) {
            if (b.getTimestamp().isBefore(start) || b.getTimestamp().isAfter(end)) continue;

            List<String> deck1 = b.getPlayer1().getDeck();
            List<String> deck2 = b.getPlayer2().getDeck();

            // Gera combos do player1
            Set<Set<String>> combos1 = generateCombos(deck1, comboSize);
            for (Set<String> combo : combos1) {
                totalCounts.put(combo, totalCounts.getOrDefault(combo, 0) + 1);
                if (b.getWinner().equals("player1")) {
                    winCounts.put(combo, winCounts.getOrDefault(combo, 0) + 1);
                }
            }

            // Gera combos do player2
            Set<Set<String>> combos2 = generateCombos(deck2, comboSize);
            for (Set<String> combo : combos2) {
                totalCounts.put(combo, totalCounts.getOrDefault(combo, 0) + 1);
                if (b.getWinner().equals("player2")) {
                    winCounts.put(combo, winCounts.getOrDefault(combo, 0) + 1);
                }
            }
        }

        List<List<String>> result = new ArrayList<>();
        for (Set<String> combo : totalCounts.keySet()) {
            int total = totalCounts.get(combo);
            int wins = winCounts.getOrDefault(combo, 0);
            double rate = (wins * 100.0) / total;

            if (rate >= winRateThreshold) {
                result.add(new ArrayList<>(combo));
            }
        }

        return result;
    }

    private Set<Set<String>> generateCombos(List<String> deck, int comboSize) {
        Set<Set<String>> result = new HashSet<>();
        generateCombosHelper(deck, comboSize, 0, new LinkedList<>(), result);
        return result;
    }

    private void generateCombosHelper(List<String> deck, int comboSize, int index, LinkedList<String> current, Set<Set<String>> result) {
        if (current.size() == comboSize) {
            result.add(new HashSet<>(current));
            return;
        }

        for (int i = index; i < deck.size(); i++) {
            current.add(deck.get(i));
            generateCombosHelper(deck, comboSize, i + 1, current, result);
            current.removeLast();
        }
    }
}