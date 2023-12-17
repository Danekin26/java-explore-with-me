package ru.practicum.ewm.server.service.publice;

import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoOut;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoOut;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
    Интерфейс сервиса для степени доступности PUBLIC
 */
public interface PublicService {

    /*
        Получить подборки событий
     */
    List<CompilationDtoOut> getSelectionEvents(Boolean pinned, Integer from, Integer size);

    /*
        Получить подборку событий по id
     */
    CompilationDtoOut getSelectionEventById(Long compId);

    /*
        Получить категории
     */
    List<CategoriesDtoOut> getAllCategories(Integer from, Integer size);

    /*
        Получить категорию по id
     */
    CategoriesDtoOut getCategories(Long catId);

    /*
        Получить события с фильтрацией
     */
    List<EventsDtoOut> getEventsByFilter(String text, List<Long> categories, Boolean paid, String rangeEnd, String rangeStart,
                                         Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    /*
        Получить событие по id
     */
    EventsDtoOut getEventById(Long idEvent, HttpServletRequest request);


}
