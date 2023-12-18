package ru.practicum.ewm.server.model.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/*
    Сущность категории на выходе
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoriesDtoOut {
    private Long id;
    private String name;
}
