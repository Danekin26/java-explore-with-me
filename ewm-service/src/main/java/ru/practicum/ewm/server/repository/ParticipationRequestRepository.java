package ru.practicum.ewm.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.server.model.entity.ParticipationRequest;
import ru.practicum.ewm.server.model.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

/*
    Репозиторий для взаимодействия с базой данных сущности заявок
 */
@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    /*
        Получить все заявки по id пользователя
     */
    List<ParticipationRequest> findAllByRequesterIdUser(Long requesterId);

    /*
        Получить заявку по id пользователя и id заявки
     */
    Optional<ParticipationRequest> findByRequesterIdUserAndIdRequest(Long requesterId, Long idRequest);

    /*
        Получить заявку по id пользователя и id события
     */
    Optional<ParticipationRequest> findByRequesterIdUserAndEventsIdEvents(Long requesterId, Long idEvents);

    /*
        Получить все заявки по id события, по id заявки и по статусу
     */
    List<ParticipationRequest> findAllByEventsIdEventsAndIdRequestInAndStatus(Long eventsId, List<Long> idRequest, RequestStatus status);

    /*
        Количество подтвержденных заявок на участие в событие
     */
    long countByEventsIdEventsAndStatus(Long eventId, RequestStatus status);

    /*
        Получить заявки по id события
     */
    List<ParticipationRequest> findAllByEventsIdEvents(Long idEvents);

    /*
        Проверить наличие заявки по id пользователя и id события
     */
    boolean existsByEventsIdEventsAndRequesterIdUser(Long idEvents, Long idUser);
}