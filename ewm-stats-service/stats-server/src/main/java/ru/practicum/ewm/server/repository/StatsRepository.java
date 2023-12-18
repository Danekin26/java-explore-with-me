package ru.practicum.ewm.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.server.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

/*
    Репозитория для общения с таблицей статистики в БД
 */
@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
    /*
        Без учета уникальных ip
     */
    List<Stats> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> allListUri);

    /*
        С учетом уникальных ip
     */
    List<Stats> findDistinctIpByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> allListUri);

    /*
        Без учета уникального ip и без списка url
     */
    List<Stats> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /*
        С учетом уникального ip и без списка url
     */
    List<Stats> findDistinctIpByTimestampBetween(LocalDateTime start, LocalDateTime end);


}
