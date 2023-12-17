package ru.practicum.ewm.server.model.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.server.model.dto.location.LocationDtoOut;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/*
    Сущность события на входе
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EventsDtoIn {
    @NotBlank
    @Length(max = 2000, min = 20)
    private String annotation;

    @NotNull
    @Positive
    private Long category;

    @NotBlank
    @Size(max = 7000, min = 20)
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    @Valid
    private LocationDtoOut location;

    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;

    private Boolean requestModeration;

    @NotBlank
    @Size(max = 120, min = 3)
    private String title;
}
