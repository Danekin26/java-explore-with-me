package ru.practicum.ewm.server.model.dto.participation_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.server.model.enums.RequestStatus;

import java.time.LocalDateTime;

/*
    Сущность заявок на выходе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDtoOut {
    private Long id;
    private Long event;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Long requester;
    private RequestStatus status;
}
