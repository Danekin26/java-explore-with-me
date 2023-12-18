package ru.practicum.ewm.server.model;

import ru.practicum.ewm.dto.StatsDtoIn;
import ru.practicum.ewm.dto.StatsDtoOut;

import java.util.*;
import java.util.stream.Collectors;

public class StatsMapper {

    public static Stats statsDtoInToStats(StatsDtoIn statsDtoIn) {
        return Stats.builder()
                .app(statsDtoIn.getApp())
                .uri(statsDtoIn.getUri())
                .ip(statsDtoIn.getIp())
                .timestamp(statsDtoIn.getTimestamp())
                .build();
    }

    public static StatsDtoOut statsToStatsDtoOut(Stats stats, Long hits) {
        return StatsDtoOut.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(hits)
                .build();
    }

    public static StatsDtoIn statsToStatsDtoIn(Stats stats) {
        return StatsDtoIn.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .ip(stats.getIp())
                .timestamp(stats.getTimestamp())
                .build();
    }

    public static List<StatsDtoOut> allStatsToAllStatsDtoOut(List<Stats> allStats, Boolean unique) {
        Map<String, Long> uriCount = new HashMap<>();
        List<Stats> uniqueStats = new ArrayList<>();
        List<StatsDtoOut> result = new ArrayList<>();



        /*
        if (unique) {
            List<StatsDtoOut> asd = allStats.stream().map(stats -> statsToStatsDtoOut(stats, 1L)).collect(Collectors.toList());
            return asd;
        } else {
            List<StatsDtoOut> asd = allStats.stream().map(stats -> statsToStatsDtoOut(stats, getRepeatIp(allStats, stats.getIp()))).distinct().collect(Collectors.toList());
            return asd;
        }*/

        if (unique) {
            return allStats.stream().map(stats -> statsToStatsDtoOut(stats, 1L)).collect(Collectors.toList());
        }
        /*List<StatsDtoOut> asd = allStats.stream().map(stats -> statsToStatsDtoOut(stats, getRepeatIp(allStats, stats.getIp()))).distinct().collect(Collectors.toList());
        return asd;*/
        for (Stats stats : allStats) {
            String uri = stats.getUri();

            if (uniqueStats.stream().noneMatch(st -> st.getUri().equals(uri))) {
                uniqueStats.add(stats);
            }

            uriCount.put(uri, uriCount.getOrDefault(uri, 0L) + 1);
        }

        for (Stats stats : uniqueStats) {
            result.add(statsToStatsDtoOut(stats, uriCount.get(stats.getUri())));
        }
        return result.stream().sorted(Comparator.comparing(StatsDtoOut::getHits).reversed()).collect(Collectors.toList());
    }

    private static Long getRepeatIp(List<Stats> allStats, String searchIp) {
        return allStats.stream().filter(stat -> stat.getIp().equals(searchIp)).count();
    }
}
