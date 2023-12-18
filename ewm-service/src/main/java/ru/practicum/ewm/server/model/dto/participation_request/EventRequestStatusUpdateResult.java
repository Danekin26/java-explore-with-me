package ru.practicum.ewm.server.model.dto.participation_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
    Сущность заявок для обновления на выходе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDtoOut> confirmedRequests;

    private List<ParticipationRequestDtoOut> rejectedRequests;
}
