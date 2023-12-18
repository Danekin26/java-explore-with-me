package ru.practicum.ewm.server.exception;

/*
    Исключение при нарушении правил введения данных
 */
public class EnteredDataDoesNotComplyWithRulesException extends RuntimeException {
    public EnteredDataDoesNotComplyWithRulesException(String mes) {
        super(mes);
    }
}
