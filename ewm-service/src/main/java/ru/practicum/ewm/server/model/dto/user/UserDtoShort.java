package ru.practicum.ewm.server.model.dto.user;

import lombok.*;

/*
    Сокращенная сущность пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoShort {
    private Long id;
    private String name;
}
