package ru.practicum.server.service;


import ru.practicum.dto.StatsDtoIn;
import ru.practicum.dto.StatsDtoOut;

import java.time.LocalDateTime;
import java.util.List;

/*
    Интерфейс сервиса статистики
 */
public interface StatsService {

    void saveStat(StatsDtoIn statsDtoIn);

    List<StatsDtoOut> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}
