package ru.practicum.ewm.server.model.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.server.model.dto.location.LocationDtoOut;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

/*
    Сущность события для обновления
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EventUpdateDtoIn {
    @Length(max = 2000, min = 20)
    private String annotation;

    @Positive
    private Long category;

    @Length(max = 7000, min = 20)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @Valid
    private LocationDtoOut location;

    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;

    private Boolean requestModeration;

    private String stateAction;

    @Length(max = 120, min = 3)
    private String title;
}
