package ru.practicum.ewm.server.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Сущность пользователя на выходе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoOut {

    private Long id;
    private String name;
    private String email;
}
