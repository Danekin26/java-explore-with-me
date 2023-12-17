package ru.practicum.ewm.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.server.exception.model.ErrorResponse;

import java.time.LocalDateTime;

/*
    Управление исключениями
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse authorizationException(final IncorrectlyCreatedRequestExceptions e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .status("BAD_REQUEST")
                .reason("Запрос составлен некорректно")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse thereIsNoDataException(final LackOfDataInDatabaseExceptions e) {
        log.error(e.getMessage());

        return ErrorResponse.builder()
                .status("NOT_FOUND")
                .reason("Данные не найдены")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse authorizationException(final ViolationOfDataUniquenessExceptions e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .status("CONFLICT")
                .reason("Введенные данные введены не корректно")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse deleteException(final DeletingDataRelatedToOtherDataExceptions e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .status("CONFLICT")
                .reason("Данные, которые пытаются быть удаленными, связанны с другими данными")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse deleteException(final EnteredDataDoesNotComplyWithRulesException e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .status("CONFLICT")
                .reason("Данные введенные в запрос не соответствуют правилам")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}