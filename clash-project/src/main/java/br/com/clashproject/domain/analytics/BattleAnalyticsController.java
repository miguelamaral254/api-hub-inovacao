package br.com.clashproject.domain.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class BattleAnalyticsController {

    private final BattleAnalyticsService service;

    @GetMapping("/win-loss-percentage")
    public Map<String, Double> getWinLossPercentage(
            @RequestParam String card,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return service.calculateWinLossPercentageByCard(card, start, end);
    }

    @GetMapping("/winning-decks")
    public List<List<String>> getWinningDecks(
            @RequestParam double percentage,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return service.findWinningDecksAboveThreshold(percentage, start, end);
    }

    @GetMapping("/defeats-by-combo")
    public long getDefeatsByCombo(
            @RequestParam List<String> cards,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return service.countDefeatsByCardCombo(cards, start, end);
    }

    @GetMapping("/special-victories")
    public long getSpecialVictories(
            @RequestParam String card,
            @RequestParam double trophyDiffPercent,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return service.countVictoriesBySpecialCondition(card, trophyDiffPercent, start, end);
    }

    @GetMapping("/winning-combos")
    public List<List<String>> getWinningCombos(
            @RequestParam int comboSize,
            @RequestParam double winRateThreshold,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return service.listWinningCardCombos(comboSize, winRateThreshold, start, end);
    }
}