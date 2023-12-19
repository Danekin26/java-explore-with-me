package ru.practicum.ewm.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.server.model.dto.comment.CommentDtoIn;
import ru.practicum.ewm.server.model.dto.comment.CommentDtoOut;
import ru.practicum.ewm.server.model.dto.events.EventUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;
import ru.practicum.ewm.server.model.dto.participation_request.EventRequestStatusUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.participation_request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.server.model.dto.participation_request.ParticipationRequestDtoOut;
import ru.practicum.ewm.server.service.privat.PrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class PrivateController {
    private final PrivateService service;

    /*
        Создать событие
     */
    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventsDtoOut createEvents(@PathVariable Long userId, @RequestBody @Valid EventsDtoIn eventsDtoIn) {
        log.info("Выполняется POST-запрос. Создание события.");
        return service.createEvents(userId, eventsDtoIn);
    }

    /*
        Добавление запроса от текущего пользователя
     */
    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDtoOut createRequest(@PathVariable Long userId, @RequestParam(name = "eventId") Long eventId) {
        log.info("Выполняется POST-запрос. Создать запрос.");
        return service.createRequest(userId, eventId);
    }

    /*
        Добавить комментарий
     */
    @PostMapping("/{userId}/event/{eventId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoOut addComment(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody @Valid CommentDtoIn commentDtoIn) {
        log.info("Выполняется POST-запрос. Добавить комментарий.");
        return service.addComment(userId, eventId, commentDtoIn);
    }

    /*
        Изменить событие пользователя
     */
    @PatchMapping("/{userId}/events/{eventId}")
    public EventsDtoOut editEvents(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody @Valid EventUpdateDtoIn eventUpdateDtoIn) {
        log.info("Выполняется PATCH-запрос. Изменить событие пользователя.");
        return service.editEvent(userId, eventId, eventUpdateDtoIn);
    }

    /*
        Изменение статуса заявок
     */
    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult editState(@PathVariable Long userId,
                                                    @PathVariable Long eventId,
                                                    @RequestBody EventRequestStatusUpdateDtoIn inputUpdate) {
        log.info("Выполняется PATCH-запрос. Изменить статус заявок.");
        return service.editState(userId, eventId, inputUpdate);
    }

    /*
        Отмена своего запроса
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDtoOut cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Выполняется PATCH-запрос. Отменить запрос.");
        return service.cancelRequest(userId, requestId);
    }

    /*
        Изменить собственный комментарий
     */
    @PatchMapping("/{userId}/comment/{commentId}")
    public CommentDtoOut editComment(@PathVariable Long userId, @PathVariable Long commentId, @RequestBody @Valid CommentDtoIn commentDtoIn) {
        log.info("Выполняется PATCH-запрос. Изменить комментарий.");
        return service.editComment(userId, commentId, commentDtoIn);
    }

    /*
        Получить заявки пользователя
     */
    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDtoOut> getRequestsByUser(@PathVariable Long userId) {
        log.info("Выполняется GET-запрос. Получение заявки пользователя.");
        return service.getRequestsByUser(userId);
    }

    /*
       Получить все события создателя по id
    */
    @GetMapping("/{userId}/events")
    public List<EventsDtoOut> getEventsByUser(@PathVariable Long userId,
                                              @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                              @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        log.info("Выполняется GET-запрос. Получение событий, созданных одним пользователем.");
        return service.getEventByUser(userId, from, size);
    }

    /*
       Получить информацию о запросах на участие в событии пользователя
    */
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDtoOut> getRequestByEventIdAndUserId(@PathVariable Long userId,
                                                                         @PathVariable Long eventId) {

        log.info("Выполняется GET-запрос. Получение запроса события пользователя.");
        return service.getParticipationRequest(userId, eventId);
    }

    /*
        Получить определенное событие по id пользователя по id
     */
    @GetMapping("/{userId}/events/{eventId}")
    public EventsDtoOut getEventByEventIdAndUserId(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Выполняется GET-запрос. Получение события пользователя.");
        return service.getEventByIdAndUserId(userId, eventId);
    }

    /*
        Получить все комментарии события
     */
    @GetMapping("/comment/{eventId}")
    public List<CommentDtoOut> getAllCommitByEvent(@PathVariable Long eventId) {
        log.info("Выполняется GET-запрос. Получение комментарии к событию.");
        return service.getAllCommentByEvent(eventId);
    }

    /*
        Удалить комментарий
     */
    @DeleteMapping("/{userId}/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Выполняется DELETE-запрос. Удалить комментарий.");
        service.removeComment(userId, commentId);
    }
}