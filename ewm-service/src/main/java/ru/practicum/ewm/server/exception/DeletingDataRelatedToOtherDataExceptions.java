package ru.practicum.ewm.server.exception;

/*
    Исключение при удалении данных, связанных с другими данными
 */
public class DeletingDataRelatedToOtherDataExceptions extends RuntimeException {
    public DeletingDataRelatedToOtherDataExceptions(String mes) {
        super(mes);
    }
}
