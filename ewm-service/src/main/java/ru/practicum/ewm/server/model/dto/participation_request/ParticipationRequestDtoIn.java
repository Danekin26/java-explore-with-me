package ru.practicum.ewm.server.model.dto.participation_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
    Сущность заявок на входе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDtoIn {
    private Long eventsId;
    private LocalDateTime created;
    private Long requesterId;
    private String status;
}
