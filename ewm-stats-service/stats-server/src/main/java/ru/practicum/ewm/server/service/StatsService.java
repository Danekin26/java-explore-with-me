package ru.practicum.ewm.server.service;


import ru.practicum.ewm.dto.StatsDtoIn;
import ru.practicum.ewm.dto.StatsDtoOut;

import java.time.LocalDateTime;
import java.util.List;

/*
    Интерфейс сервиса статистики
 */
public interface StatsService {

    StatsDtoIn saveStat(StatsDtoIn statsDtoIn);

    List<StatsDtoOut> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}
