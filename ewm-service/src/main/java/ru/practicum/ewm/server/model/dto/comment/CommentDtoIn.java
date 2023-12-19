package ru.practicum.ewm.server.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/*
    Сущность комментарий на входе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoIn {
    @NotBlank
    @Size(max = 4000, min = 1)
    private String comment;
}
