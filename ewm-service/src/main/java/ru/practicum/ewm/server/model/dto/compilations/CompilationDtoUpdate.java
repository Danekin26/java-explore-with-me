package ru.practicum.ewm.server.model.dto.compilations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

/*
    Сущность подборки для обновления
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDtoUpdate {
    private List<Long> events;
    private Boolean pinned = false;

    @Size(max = 50, min = 1)
    private String title;
}
