package ru.practicum.ewm.server.model.dto.participation_request;

import ru.practicum.ewm.server.model.entity.Events;
import ru.practicum.ewm.server.model.entity.ParticipationRequest;
import ru.practicum.ewm.server.model.entity.User;
import ru.practicum.ewm.server.model.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
    Маппер сущностей заявок
 */
public class ParticipationRequestMapper {

    public static ParticipationRequest participationRequestDtoInToParticipationRequest(Events event, User user) {
        return ParticipationRequest.builder()
                .events(event)
                .status(!event.getRequestModeration() || event.getParticipantLimit() == 0 ? RequestStatus.CONFIRMED : RequestStatus.PENDING)
                .created(LocalDateTime.now())
                .requester(user)
                .build();
    }

    public static ParticipationRequestDtoOut participationRequestToParticipationRequestDtoOut(ParticipationRequest participationRequest) {
        return ParticipationRequestDtoOut.builder()
                .id(participationRequest.getIdRequest())
                .event(participationRequest.getEvents().getIdEvents())
                .created(participationRequest.getCreated())
                .requester(participationRequest.getIdRequest())
                .status(participationRequest.getStatus())
                .build();
    }

    public static List<ParticipationRequestDtoOut> allRequestToAllRequestDtoOut(List<ParticipationRequest> allRequest) {
        return allRequest.stream().map(ParticipationRequestMapper::participationRequestToParticipationRequestDtoOut).collect(Collectors.toList());
    }
}
