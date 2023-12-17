package ru.practicum.ewm.server.model.dto.compilations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewm.server.model.dto.events.EventDtoShort;

import java.util.Set;

/*
    Сущность подборки на выходе
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CompilationDtoOut {
    private Long id;
    private Set<EventDtoShort> events;
    private Boolean pinned;
    private String title;
}
