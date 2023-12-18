package ru.practicum.ewm.server.model.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoOut;
import ru.practicum.ewm.server.model.dto.location.LocationDtoOut;
import ru.practicum.ewm.server.model.enums.StateEnum;
import ru.practicum.ewm.server.model.dto.user.UserDtoOut;

import java.time.LocalDateTime;

/*
    Сущность события на выходе
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EventsDtoOut {
    private Long id;
    private String annotation;
    private CategoriesDtoOut category;
    private Long confirmedRequests;
    private UserDtoOut initiator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private LocationDtoOut location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
    private StateEnum state;
    private Long views;
}