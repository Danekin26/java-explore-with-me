package ru.practicum.ewm.server.exception;

import lombok.Data;

/*
    Сущность исключения
 */
@Data
public class ErrorResponse {
    private final String error;
}
