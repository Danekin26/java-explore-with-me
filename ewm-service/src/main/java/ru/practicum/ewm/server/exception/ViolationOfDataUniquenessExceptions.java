package ru.practicum.ewm.server.exception;

/*
    Исключение при нарушении уникальности данных в базе
 */
public class ViolationOfDataUniquenessExceptions extends RuntimeException {
    public ViolationOfDataUniquenessExceptions(String mes) {
        super(mes);
    }
}
