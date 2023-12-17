package ru.practicum.ewm.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoOut;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoOut;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;
import ru.practicum.ewm.server.service.publice.PublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicController {
    private final PublicService service;

    /*
        Получить подборки событий
     */
    @GetMapping("/compilations")
    public List<CompilationDtoOut> getSelectionEvents(@RequestParam(defaultValue = "false") Boolean pinned,
                                                      @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                                      @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        log.info("Выполняется GET-запрос. Получить все подборки событий.");
        return service.getSelectionEvents(pinned, from, size);
    }

    /*
        Получить подборку событий по ID
     */
    @GetMapping("/compilations/{compId}")
    public CompilationDtoOut getSelectionEventById(@PathVariable Long compId) {
        log.info("Выполняется GET-запрос. Получить подборку событий с id {}.", compId);
        return service.getSelectionEventById(compId);
    }

    /*
        Получить категории
     */
    @GetMapping("/categories")
    public List<CategoriesDtoOut> getAllCategories(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Выполняется GET-запрос. Получить все категории.");
        return service.getAllCategories(from, size);
    }

    /*
        Получить категорию по ID
     */
    @GetMapping("/categories/{catId}")
    public CategoriesDtoOut getCategoriesById(@PathVariable Long catId) {
        log.info("Выполняется GET-запрос. Получить все категории.");
        return service.getCategories(catId);
    }

    /*
        Получить события с фильтрацией
     */
    @GetMapping("/events")
    public List<EventsDtoOut> getEventsByFilter(@RequestParam(required = false) String text,
                                                @RequestParam(required = false) List<Long> categories,
                                                @RequestParam(required = false) Boolean paid,
                                                @RequestParam(required = false) String rangeEnd,
                                                @RequestParam(required = false) String rangeStart,
                                                @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size,
                                                HttpServletRequest request) {
        log.info("Выполняется GET-запрос. Получить все категории.");
        return service.getEventsByFilter(text, categories, paid, rangeEnd, rangeStart, onlyAvailable, sort, from, size, request);
    }

    /*
        Получить событие по ID
     */
    @GetMapping("/events/{id}")
    public EventsDtoOut getEventsById(@PathVariable Long id, HttpServletRequest request) {
        log.info("Выполняется GET-запрос. Получить событие по id {}.", id);
        return service.getEventById(id, request);
    }
}
