package ru.practicum.ewm.server.exception;

/*
    Исключение при вводе даты в будущем времени
 */
public class BeginningOfDateInFutureTimeException extends RuntimeException {
    public BeginningOfDateInFutureTimeException(String mes) {
        super(mes);
    }
}
