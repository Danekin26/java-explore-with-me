package ru.practicum.ewm.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
}
