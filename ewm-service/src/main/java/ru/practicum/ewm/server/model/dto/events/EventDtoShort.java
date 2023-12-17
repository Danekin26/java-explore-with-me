package ru.practicum.ewm.server.model.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoOut;
import ru.practicum.ewm.server.model.dto.user.UserDtoShort;

import java.time.LocalDateTime;

/*
    Сущность события сокращенная
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDtoShort {
    private Long id;
    private String annotation;
    private CategoriesDtoOut category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserDtoShort initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
