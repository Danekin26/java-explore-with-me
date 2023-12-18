package ru.practicum.ewm.server.model.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Сущность места мероприятия на входе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDtoIn {
    private Float lat;
    private Float lon;
}
