package ru.practicum.ewm.server.exception;

/*
    Исключение при отсутствии данных в БД
 */
public class LackOfDataInDatabaseExceptions extends RuntimeException {
    public LackOfDataInDatabaseExceptions(String mes) {
        super(mes);
    }
}
