package ru.practicum.server.exception;

import lombok.Data;

/*
    Сущность исключения
 */
@Data
public class ErrorResponse {
    private final String error;
}
