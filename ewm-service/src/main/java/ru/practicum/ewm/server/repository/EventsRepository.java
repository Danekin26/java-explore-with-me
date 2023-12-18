package ru.practicum.ewm.server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.server.model.entity.Categories;
import ru.practicum.ewm.server.model.entity.Events;
import ru.practicum.ewm.server.model.enums.StateEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
    Репозиторий для взаимодействия с базой данных сущности событий
 */
@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {

    /*
        Получить все события пользователя по id
     */
    List<Events> findAllByInitiatorIdUser(Long initiatorId, Pageable pageable);

    /*
        Получить определенное событие пользователя
     */
    Optional<Events> findEventsByIdEventsAndInitiatorIdUser(Long idUser, Long idEvents);

    /*
        Поиск по фильтрам public
     */
    @Query("SELECT e FROM Events e " +
            "WHERE " +
            "(:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categories IS NULL OR e.category.idCategories IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate >= COALESCE(:rangeStart, e.eventDate) " +
            "AND e.eventDate <= COALESCE(:rangeEnd, e.eventDate) " +
            "AND (:onlyAvailable IS NULL OR e.confirmedRequests < e.participantLimit) " +
            "AND e.state = 'PUBLISHED' " +
            "ORDER BY " +
            "CASE WHEN :sort = 'EVENT_DATE' THEN e.eventDate END ASC, " +
            "CASE WHEN :sort = 'VIEWS' THEN e.views END DESC")
    List<Events> findEventsByFilters(
            @Param("text") String text,
            @Param("categories") List<Long> categories,
            @Param("paid") Boolean paid,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            @Param("onlyAvailable") Boolean onlyAvailable,
            @Param("sort") String sort,
            Pageable pageable);

    /*
        Поиск по фильтрам admin
     */
    @Query("SELECT e FROM Events e " +
            "WHERE (:users IS NULL OR e.initiator.idUser IN :users) " +
            "AND (:states IS NULL OR e.state IN :states) " +
            "AND (:categories IS NULL OR e.category.idCategories IN :categories) " +
            "AND e.eventDate >= COALESCE(:rangeStart, e.eventDate) " +
            "AND e.eventDate <= COALESCE(:rangeEnd, e.eventDate) " +
            "ORDER BY e.eventDate DESC")
    List<Events> findEventsByFilters(@Param("users") List<Long> users,
                                     @Param("states") List<StateEnum> states,
                                     @Param("categories") List<Long> categoryIdCategories,
                                     @Param("rangeStart") LocalDateTime rangeStart,
                                     @Param("rangeEnd") LocalDateTime rangeEnd,
                                     Pageable pageable);

    /*
        Получить события пользователей
     */
    Set<Events> findByIdEventsIn(List<Long> idEvent);

    /*
        Проверить наличие категории
     */
    boolean existsByCategory(Categories category);
}
