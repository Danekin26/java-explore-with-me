package ru.practicum.ewm.server.model.dto.events;

import ru.practicum.ewm.server.model.dto.categories.CategoriesMapper;
import ru.practicum.ewm.server.model.dto.location.LocationMapper;
import ru.practicum.ewm.server.model.entity.Categories;
import ru.practicum.ewm.server.model.entity.Events;
import ru.practicum.ewm.server.model.entity.User;
import ru.practicum.ewm.server.model.enums.StateEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.server.model.dto.user.UserMapper.userToUserDtoOut;
import static ru.practicum.ewm.server.model.dto.user.UserMapper.userToUserDtoShort;

/*
    Маппер сущности события
 */
public class EventsMapper {

    public static Events eventsDtoInToEvents(EventsDtoIn eventsDtoIn, Categories categories, User initiator) {
        return Events.builder()
                .annotation(eventsDtoIn.getAnnotation())
                .category(categories)
                .confirmedRequests(0L)
                .createdOn(LocalDateTime.now())
                .description(eventsDtoIn.getDescription())
                .eventDate(eventsDtoIn.getEventDate())
                .initiator(initiator)
                .location(LocationMapper.locationDtoOutToLocation(eventsDtoIn.getLocation()))
                .paid(eventsDtoIn.getPaid())
                .participantLimit(eventsDtoIn.getParticipantLimit())
                .publishedOn(null)
                .requestModeration(eventsDtoIn.getRequestModeration())
                .state(StateEnum.PENDING)
                .views(0L)
                .title(eventsDtoIn.getTitle())
                .build();
    }

    public static EventsDtoOut eventsToEventsDtoOut(Events events) {
        return EventsDtoOut.builder()
                .id(events.getIdEvents())
                .annotation(events.getAnnotation())
                .category(CategoriesMapper.categoriesToCategoriesDtoOut(events.getCategory()))
                .confirmedRequests(events.getConfirmedRequests())
                .initiator(userToUserDtoOut(events.getInitiator()))
                .createdOn(events.getCreatedOn())
                .description(events.getDescription())
                .eventDate(events.getEventDate())
                .publishedOn(events.getPublishedOn())
                .location(LocationMapper.locationToLocationDtoOut(events.getLocation()))
                .paid(events.getPaid())
                .participantLimit(events.getParticipantLimit())
                .requestModeration(events.getRequestModeration())
                .title(events.getTitle())
                .state(events.getState())
                .views(events.getViews())
                .build();
    }

    public static List<EventsDtoOut> allEventToEventDtoOut(List<Events> allEvents) {
        return allEvents.stream().map(EventsMapper::eventsToEventsDtoOut).collect(Collectors.toList());
    }

    public static Events eventsUpdateDtoMergeEvents(EventUpdateDtoIn eventUpdateDtoIn, Events event, Categories categories) {
        return Events.builder()
                .idEvents(event.getIdEvents())
                .annotation(eventUpdateDtoIn.getAnnotation() == null ? event.getAnnotation() : eventUpdateDtoIn.getAnnotation())
                .category(eventUpdateDtoIn.getCategory() == null ? event.getCategory() : categories)
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(eventUpdateDtoIn.getDescription() == null ? event.getDescription() : eventUpdateDtoIn.getDescription())
                .eventDate(eventUpdateDtoIn.getEventDate() == null ? event.getEventDate() : eventUpdateDtoIn.getEventDate())
                .initiator(event.getInitiator())
                .location(eventUpdateDtoIn.getLocation() == null ? event.getLocation() : LocationMapper.locationDtoOutToLocation(eventUpdateDtoIn.getLocation()))
                .paid(eventUpdateDtoIn.getPaid() == null ? event.getPaid() : eventUpdateDtoIn.getPaid())
                .participantLimit(eventUpdateDtoIn.getParticipantLimit() == null ? event.getParticipantLimit() : eventUpdateDtoIn.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(eventUpdateDtoIn.getRequestModeration() == null ? event.getRequestModeration() : eventUpdateDtoIn.getRequestModeration())
                .state(eventUpdateDtoIn.getStateAction() == null ? event.getState() : StateEnum.valueOf(eventUpdateDtoIn.getStateAction()))
                .views(event.getViews())
                .title(eventUpdateDtoIn.getTitle() == null ? event.getTitle() : eventUpdateDtoIn.getTitle())
                .build();
    }

    public static EventDtoShort eventToEventDtoShort(Events event) {
        return EventDtoShort.builder()
                .id(event.getIdEvents())
                .annotation(event.getAnnotation())
                .category(CategoriesMapper.categoriesToCategoriesDtoOut(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(userToUserDtoShort(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Set<EventDtoShort> allEventsToAllEventsDtoShort(Set<Events> allEvents) {
        return allEvents.stream().map(EventsMapper::eventToEventDtoShort).collect(Collectors.toSet());
    }
}
