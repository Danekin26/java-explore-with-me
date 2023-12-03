package ru.practicum.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatsDtoIn;
import ru.practicum.dto.StatsDtoOut;
import ru.practicum.server.exception.BeginningOfDateInFutureTimeException;
import ru.practicum.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.server.model.StatsMapper.allStatsToAllStatsDtoOut;
import static ru.practicum.server.model.StatsMapper.statsDtoInToStats;

/*
    Сервис статистики
 */
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    /*
        Сохранить статистику
     */
    @Override
    public void saveStat(StatsDtoIn statsDtoIn) {
        repository.save(statsDtoInToStats(statsDtoIn));
    }

    /*
        Получить статистику
     */
    @Override
    public List<StatsDtoOut> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(LocalDateTime.now())) {
            throw new BeginningOfDateInFutureTimeException("Дата в периоде указана в будущем времени");
        }

        if (uris.isEmpty()) {
            if (unique) {
                return allStatsToAllStatsDtoOut(repository.findDistinctIpByTimestampBetween(start, end), unique);
            } else {
                return allStatsToAllStatsDtoOut(repository.findAllByTimestampBetween(start, end), unique);
            }
        } else {
            if (unique) {
                return allStatsToAllStatsDtoOut(repository.findDistinctIpByTimestampBetweenAndUriIn(start, end, uris), unique);
            } else {
                return allStatsToAllStatsDtoOut(repository.findAllByTimestampBetweenAndUriIn(start, end, uris), unique);
            }
        }
    }
}
