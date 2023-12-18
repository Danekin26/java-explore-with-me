package ru.practicum.ewm.server.model.dto.participation_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.server.model.enums.RequestStatus;

import java.util.List;

/*
    Сущность заявок для обновления на входе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateDtoIn {
    private List<Long> requestIds;
    private RequestStatus status;
}
