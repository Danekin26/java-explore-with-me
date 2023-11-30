package ru.practicum.dto;

public class StatsMapper {
    public static StatsDtoOut statsDtoInToStatsDtoOut(StatsDtoIn statsDtoIn, Integer hits) {
        return StatsDtoOut.builder()
                .app(statsDtoIn.getApp())
                .uri(statsDtoIn.getUri())
                .hits(hits)
                .build();
    }
}
