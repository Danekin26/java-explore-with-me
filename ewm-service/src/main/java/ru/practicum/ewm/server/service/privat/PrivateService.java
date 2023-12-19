package ru.practicum.ewm.server.service.privat;

import ru.practicum.ewm.server.model.dto.comment.CommentDtoIn;
import ru.practicum.ewm.server.model.dto.comment.CommentDtoOut;
import ru.practicum.ewm.server.model.dto.events.EventUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;
import ru.practicum.ewm.server.model.dto.participation_request.EventRequestStatusUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.participation_request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.server.model.dto.participation_request.ParticipationRequestDtoOut;

import java.util.List;

/*
    Интерфейс сервиса для степени доступности PRIVATE
 */
public interface PrivateService {
    /*
        Cоздать событие
     */
    EventsDtoOut createEvents(Long userId, EventsDtoIn eventsDtoIn);

    /*
        Получить все события пользователя
     */
    List<EventsDtoOut> getEventByUser(Long userId, Integer from, Integer size);

    /*
        Получить событие пользователя
     */
    EventsDtoOut getEventByIdAndUserId(Long userId, Long eventId);

    /*
        Обновить событие
     */
    EventsDtoOut editEvent(Long userId, Long eventId, EventUpdateDtoIn eventUpdateDtoIn);

    /*
        Получить информацию о запросах на участие в событии пользователя
     */
    List<ParticipationRequestDtoOut> getParticipationRequest(Long userId, Long eventId);

    /*
        Изменить статус
     */
    EventRequestStatusUpdateResult editState(Long userId, Long eventId, EventRequestStatusUpdateDtoIn eventRequestStatusUpdateDtoIn);

    /*
        Создать запрос
     */
    ParticipationRequestDtoOut createRequest(Long userId, Long eventId);

    /*
        Отменить запрос
     */
    ParticipationRequestDtoOut cancelRequest(Long userId, Long requestId);

    /*
        Получить заявки пользователя
     */
    List<ParticipationRequestDtoOut> getRequestsByUser(Long userId);

    /*
        Создать комментарий
     */
    CommentDtoOut addComment(Long userId, Long eventId, CommentDtoIn commentDtoIn);

    /*
        Изменить личный комментарий
     */
    CommentDtoOut editComment(Long userId, Long commentId, CommentDtoIn commentDtoIn);

    /*
        Удалить личный комментарий
     */
    void removeComment(Long userId, Long commentId);

    /*
        Вывести комментарии к событию
     */
    List<CommentDtoOut> getAllCommentByEvent(Long eventId);
}
