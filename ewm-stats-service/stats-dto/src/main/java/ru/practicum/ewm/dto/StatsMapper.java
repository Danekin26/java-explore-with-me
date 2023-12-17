package ru.practicum.ewm.dto;

public class StatsMapper {
    public static StatsDtoOut statsDtoInToStatsDtoOut(StatsDtoIn statsDtoIn, Long hits) {
        return StatsDtoOut.builder()
                .app(statsDtoIn.getApp())
                .uri(statsDtoIn.getUri())
                .hits(hits)
                .build();
    }
}
