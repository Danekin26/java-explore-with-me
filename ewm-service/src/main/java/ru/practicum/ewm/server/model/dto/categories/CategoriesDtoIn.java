package ru.practicum.ewm.server.model.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/*
    Сущности категории на входе
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoriesDtoIn {
    @NotBlank
    @Size(max = 50, min = 1)
    private String name;
}
