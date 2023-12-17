package ru.practicum.ewm.server.util;

import ru.practicum.ewm.server.exception.IncorrectlyCreatedRequestExceptions;

public class ValidationDate {
    public static void checkNameCategories(String name) {
        if (name == null) {
            throw new IncorrectlyCreatedRequestExceptions("Название отсутствует");
        }
    }
}
