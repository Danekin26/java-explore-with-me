package ru.practicum.server.model;

import ru.practicum.dto.StatsDtoIn;
import ru.practicum.dto.StatsDtoOut;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsMapper {

    public static Stats statsDtoInToStats(StatsDtoIn statsDtoIn) {
        return Stats.builder()
                .app(statsDtoIn.getApp())
                .uri(statsDtoIn.getUri())
                .ip(statsDtoIn.getIp())
                .timestamp(LocalDateTime.parse(statsDtoIn.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static StatsDtoOut statsToStatsDtoOut(Stats stats, Integer hits) {
        return StatsDtoOut.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(hits)
                .build();
    }

    public static List<StatsDtoOut> allStatsToAllStatsDtoOut(List<Stats> allStats, Boolean unique) {
        Map<String, Integer> uriCount = new HashMap<>();
        List<Stats> uniqueStats = new ArrayList<>();
        List<StatsDtoOut> result = new ArrayList<>();

        if (unique) {
            return allStats.stream().map(stats -> statsToStatsDtoOut(stats, 1)).collect(Collectors.toList());
        }

        for (Stats stats : allStats) {
            String uri = stats.getUri();

            if (uniqueStats.stream().noneMatch(st -> st.getUri().equals(uri))) {
                uniqueStats.add(stats);
            }

            uriCount.put(uri, uriCount.getOrDefault(uri, 0) + 1);
        }

        for (Stats stats : uniqueStats) {
            result.add(statsToStatsDtoOut(stats, uriCount.get(stats.getUri())));
        }
        return result.stream().sorted(Comparator.comparing(StatsDtoOut::getHits).reversed()).collect(Collectors.toList());
    }
}
