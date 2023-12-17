package ru.practicum.ewm.server.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/*
    Сущность пользователя на входе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoIn {
    @NotBlank
    @Size(max = 250, min = 2)
    private String name;

    @NotBlank
    @Email
    @Size(max = 254, min = 6)
    private String email;
}
