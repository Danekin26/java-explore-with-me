package ru.practicum.ewm.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.StatsDtoIn;
import ru.practicum.ewm.dto.StatsDtoOut;
import ru.practicum.ewm.server.exception.BeginningOfDateInFutureTimeException;
import ru.practicum.ewm.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.server.model.StatsMapper.*;

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
    public StatsDtoIn saveStat(StatsDtoIn statsDtoIn) {
        return statsToStatsDtoIn(repository.save(statsDtoInToStats(statsDtoIn)));
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
