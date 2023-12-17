package ru.practicum.ewm.server.exception;

/*
    Исключение при некорректно составленном запросе
 */
public class IncorrectlyCreatedRequestExceptions extends RuntimeException {
    public IncorrectlyCreatedRequestExceptions(String mes) {
        super(mes);
    }
}
