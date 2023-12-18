package ru.practicum.ewm.server.service.privat;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.server.exception.IncorrectlyCreatedRequestExceptions;
import ru.practicum.ewm.server.exception.InvalidDataEnteredException;
import ru.practicum.ewm.server.exception.LackOfDataInDatabaseExceptions;
import ru.practicum.ewm.server.exception.ViolationOfDataUniquenessExceptions;
import ru.practicum.ewm.server.model.dto.events.EventUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;
import ru.practicum.ewm.server.model.dto.participation_request.EventRequestStatusUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.participation_request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.server.model.dto.participation_request.ParticipationRequestDtoOut;
import ru.practicum.ewm.server.model.entity.*;
import ru.practicum.ewm.server.model.enums.RequestStatus;
import ru.practicum.ewm.server.model.enums.StateEnum;
import ru.practicum.ewm.server.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.practicum.ewm.server.model.dto.events.EventsMapper.*;
import static ru.practicum.ewm.server.model.dto.location.LocationMapper.locationDtoOutToLocation;
import static ru.practicum.ewm.server.model.dto.participation_request.ParticipationRequestMapper.*;
import static ru.practicum.ewm.server.model.enums.RequestStatus.CONFIRMED;
import static ru.practicum.ewm.server.model.enums.RequestStatus.REJECTED;
import static ru.practicum.ewm.server.model.enums.StateActionEnum.CANCEL_REVIEW;
import static ru.practicum.ewm.server.model.enums.StateActionEnum.SEND_TO_REVIEW;

/*
    Имплементация сервиса для степени доступности PRIVATE
 */
@Service
@AllArgsConstructor
public class PrivateServiceImpl implements PrivateService {
    private final EventsRepository eventsRepository;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository participationRequestRepository;
    private final LocationRepository locationRepository;

    @Override
    public EventsDtoOut createEvents(Long userId, EventsDtoIn eventsDtoIn) {
        if (eventsDtoIn.getEventDate() != null && eventsDtoIn.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IncorrectlyCreatedRequestExceptions("Дата при изменении события не может быть уже наступившей");
        }

        User initiator = userRepository.findById(userId).orElseThrow(); //TODO проверка на наличие юзера
        Categories categories = categoriesRepository.findById(eventsDtoIn.getCategory()).orElseThrow(); //TODO проверка на наличие категории
        Location location = locationRepository.save(locationDtoOutToLocation(eventsDtoIn.getLocation()));
        Events events = eventsDtoInToEvents(eventsDtoIn, categories, initiator);
        events.setLocation(location);

        events.setRequestModeration(eventsDtoIn.getRequestModeration() == null || eventsDtoIn.getRequestModeration());
        events.setParticipantLimit(eventsDtoIn.getParticipantLimit() == null ? 0 : eventsDtoIn.getParticipantLimit());
        events.setPaid(eventsDtoIn.getPaid() != null && eventsDtoIn.getPaid());

        return eventsToEventsDtoOut(eventsRepository.save(events));
    }

    @Override
    public List<EventsDtoOut> getEventByUser(Long userId, Integer from, Integer size) {
        if (from < 0 || size < 1) throw new InvalidDataEnteredException("size и/или from введены не корректно");
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (!userRepository.existsById(userId)) {
            return new ArrayList<>();
        }
        return allEventToEventDtoOut(eventsRepository.findAllByInitiatorIdUser(userId, pageRequest));
    }

    @Override
    public EventsDtoOut getEventByIdAndUserId(Long userId, Long eventId) {
        Events resulSearch = eventsRepository.findEventsByIdEventsAndInitiatorIdUser(eventId, userId).orElseThrow(
                () -> new LackOfDataInDatabaseExceptions(String.format("События с id %d у пользователя с id %d не существует",
                        eventId, userId)));
        return eventsToEventsDtoOut(resulSearch);
    }

    @Override
    public EventsDtoOut editEvent(Long userId, Long eventId, EventUpdateDtoIn eventUpdateDtoIn) {
        Events event = eventsRepository.findEventsByIdEventsAndInitiatorIdUser(eventId, userId).orElseThrow(); // TODO обработать
        Categories categories = null;

        if (eventUpdateDtoIn.getEventDate() != null && eventUpdateDtoIn.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IncorrectlyCreatedRequestExceptions("Дата при изменении события не может быть уже наступившей");
        }

        if (event.getState() == StateEnum.PUBLISHED) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Событие %d уже опубликовано, изменить нельзя", eventId));
        }

        if (eventUpdateDtoIn.getCategory() != null) {
            categories = categoriesRepository.findById(eventUpdateDtoIn.getCategory()).orElseThrow(); // TODO обработать
        }
        if (eventUpdateDtoIn.getStateAction() != null) {
            if (eventUpdateDtoIn.getStateAction().equals(SEND_TO_REVIEW.toString())) {
                eventUpdateDtoIn.setStateAction("PENDING");
            } else if (eventUpdateDtoIn.getStateAction().equals(CANCEL_REVIEW.toString())) {
                eventUpdateDtoIn.setStateAction("CANCELED");
            } else {
                throw new IncorrectlyCreatedRequestExceptions(String.format("Статус %s не существует", eventUpdateDtoIn.getStateAction()));
            }
        }
        event = eventsUpdateDtoMergeEvents(eventUpdateDtoIn, event, categories);
        return eventsToEventsDtoOut(eventsRepository.save(event));
    }

    @Override
    public List<ParticipationRequestDtoOut> getParticipationRequest(Long userId, Long eventId) {

        User user = userRepository.findById(userId).orElseThrow(); //TODO обработать
        Events events = eventsRepository.findById(eventId).orElseThrow(); // TODO обработать
        if (!Objects.equals(events.getInitiator().getIdUser(), user.getIdUser())) {
            throw new LackOfDataInDatabaseExceptions(String.format("События с id = %d и создателем с id = %d не существует", eventId, userId));
        }
        List<ParticipationRequest> allRequest = participationRequestRepository.findAllByEventsIdEvents(eventId);
        return allRequestToAllRequestDtoOut(allRequest);
    }

    @Override
    public EventRequestStatusUpdateResult editState(Long userId, Long eventId, EventRequestStatusUpdateDtoIn eventRequestStatusUpdateDtoIn) {
        Events event = eventsRepository.findEventsByIdEventsAndInitiatorIdUser(eventId, userId).orElseThrow();// TODO throws

        Optional<ParticipationRequest> searchRequest = participationRequestRepository.findByRequesterIdUserAndEventsIdEvents(userId, eventId);

        if (searchRequest.isPresent() && searchRequest.get().getStatus() == REJECTED) {
            throw new ViolationOfDataUniquenessExceptions(String.format("У заявки %d нельзя изменить статус", searchRequest.get().getIdRequest()));
        }

        if (event.getConfirmedRequests() + 1 > event.getParticipantLimit()) {
            throw new ViolationOfDataUniquenessExceptions(String.format("У события %d уже максимум участников", eventId));
        }

        List<ParticipationRequest> allParticipationRequest =
                participationRequestRepository.findAllByEventsIdEventsAndIdRequestInAndStatus(
                        eventId, eventRequestStatusUpdateDtoIn.getRequestIds(), RequestStatus.PENDING);
        List<ParticipationRequestDtoOut> confirmed = new ArrayList<>();
        List<ParticipationRequestDtoOut> rejected = new ArrayList<>();
        long confirmedRequests = participationRequestRepository.countByEventsIdEventsAndStatus(eventId, CONFIRMED);

        for (ParticipationRequest request : allParticipationRequest) {
            if (eventRequestStatusUpdateDtoIn.getStatus() == REJECTED) {
                request.setStatus(REJECTED);
                rejected.add(participationRequestToParticipationRequestDtoOut(request));
            } else if (eventRequestStatusUpdateDtoIn.getStatus() == CONFIRMED && event.getParticipantLimit() > 0 &&
                    (confirmedRequests + 1) <= event.getParticipantLimit()) {
                request.setStatus(CONFIRMED);
                confirmed.add(participationRequestToParticipationRequestDtoOut(request));
                event.setConfirmedRequests(confirmedRequests + 1);
                eventsRepository.save(event);
            } else {
                request.setStatus(REJECTED);
                event.setConfirmedRequests(confirmedRequests - 1);
                rejected.add(participationRequestToParticipationRequestDtoOut(request));
            }
            participationRequestRepository.save(request);
        }
        return EventRequestStatusUpdateResult.builder()
                .rejectedRequests(rejected)
                .confirmedRequests(confirmed)
                .build();
    }

    @Override
    public ParticipationRequestDtoOut createRequest(Long userId, Long eventId) {
        if (participationRequestRepository.existsByEventsIdEventsAndRequesterIdUser(eventId, userId)) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Заявка события id %d и пользователя id  %d уже опубликована", eventId, userId));
        }

        User user = userRepository.findById(userId).orElseThrow(); //TODO throw
        Events events = eventsRepository.findById(eventId).orElseThrow(); // TODO throw

        if (Objects.equals(user.getIdUser(), events.getInitiator().getIdUser())) {
            throw new ViolationOfDataUniquenessExceptions(String.format(
                    "Пользователь id %d является создателем события %d и не может создать заявку на участие события", eventId, userId));
        }

        if (events.getState() != StateEnum.PUBLISHED) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Заявка события id %d не может быть создана из-за статуса события", eventId));
        }

        if (Objects.equals(events.getConfirmedRequests(), events.getParticipantLimit()) && events.getParticipantLimit() != 0) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Заявка события id %d не может быть создана из-за недостатка мест события", eventId));
        }
        ParticipationRequest origin = participationRequestDtoInToParticipationRequest(events, user);
        if (origin.getStatus() == CONFIRMED) {
            events.setConfirmedRequests(events.getConfirmedRequests() + 1);
            eventsRepository.save(events);
            origin.setEvents(events);
        }
        ParticipationRequest savedRequest = participationRequestRepository.save(origin);
        return participationRequestToParticipationRequestDtoOut(savedRequest);
    }

    @Override
    public ParticipationRequestDtoOut cancelRequest(Long userId, Long requestId) {
        ParticipationRequest request = participationRequestRepository.findByRequesterIdUserAndIdRequest(userId, requestId).orElseThrow(); //TODO throws
        request.setStatus(RequestStatus.CANCELED);
        return participationRequestToParticipationRequestDtoOut(request);
    }

    @Override
    public List<ParticipationRequestDtoOut> getRequestsByUser(Long userId) {
        return allRequestToAllRequestDtoOut(participationRequestRepository.findAllByRequesterIdUser(userId));
    }
}