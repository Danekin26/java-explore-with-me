package ru.practicum.ewm.server.exception;

/*
    Исключение при неверно введен
 */
public class InvalidDataEnteredException extends RuntimeException {
    public InvalidDataEnteredException(String mes) {
        super(mes);
    }
}
