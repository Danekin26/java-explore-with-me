package ru.practicum.ewm.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoIn;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoOut;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoIn;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoOut;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoUpdate;
import ru.practicum.ewm.server.model.dto.events.EventUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;
import ru.practicum.ewm.server.model.dto.user.UserDtoIn;
import ru.practicum.ewm.server.model.dto.user.UserDtoOut;
import ru.practicum.ewm.server.model.enums.StateEnum;
import ru.practicum.ewm.server.service.admin.AdminService;

import javax.validation.Valid;
import java.util.List;

/*
    Контроллер для степени доступности ADMIN
 */

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService service; // TODO заменить на интерфейсы

    /*
        Создать новую категорию
     */
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriesDtoOut createCategories(@RequestBody @Valid CategoriesDtoIn categoriesDtoIn) {
        log.info("Выполняется POST-запрос. Создание категории {}", categoriesDtoIn);
        return service.createCategories(categoriesDtoIn);
    }

    /*
        Создать сотрудника
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoOut createUser(@RequestBody @Valid UserDtoIn userDtoIn) {
        log.info("Выполняется POST-запрос. Создать пользователя.");
        return service.createUser(userDtoIn);
    }


    /*
        Добавить подборку
     */
    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDtoOut createCompilation(@RequestBody @Valid CompilationDtoIn compilationDtoIn) {
        log.info("Выполняется POST-запрос. Создать подборку.");
        return service.createCompilation(compilationDtoIn);
    }

    /*
        Изменить подборку
     */
    @PatchMapping("/compilations/{compId}")
    public CompilationDtoOut updateCompilation(@RequestBody @Valid CompilationDtoUpdate compilationDtoUpdate, @PathVariable Long compId) {
        log.info("Выполняется PATCH-запрос. Обновить подборку.");
        return service.updateCompilations(compId, compilationDtoUpdate);
    }

    /*
        Редактирование категории
     */
    @PatchMapping("/categories/{catId}")
    public CategoriesDtoOut editCategories(@PathVariable Long catId, @RequestBody @Valid CategoriesDtoIn editCategoriesDtoIn) {
        log.info("Выполняется PATCH-запрос. Редактирование категории с id = {}", catId);
        return service.editCategories(catId, editCategoriesDtoIn);
    }

    /*
        Редактировать данные события и его статус
     */
    @PatchMapping("/events/{eventId}")
    public EventsDtoOut editEventById(@PathVariable Long eventId, @RequestBody @Valid EventUpdateDtoIn eventsDtoIn) {
        log.info("Выполняется PATCH-запрос. Изменить событие.");
        return service.editEvent(eventId, eventsDtoIn);
    }

    /*
       Получить события по параметрам
    */
    @GetMapping("/events")
    public List<EventsDtoOut> getEventsByFilter(@RequestParam(name = "users", required = false) List<Long> users,
                                                @RequestParam(name = "states", required = false) List<StateEnum> states,
                                                @RequestParam(name = "categories", required = false) List<Long> categories,
                                                @RequestParam(required = false) String rangeStart,
                                                @RequestParam(required = false) String rangeEnd,
                                                @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Выполняется GET-запрос. Получить все категории.");
        return service.getEventsByFilter(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /*
        Получить всех сотрудников
     */
    @GetMapping("/users")
    public List<UserDtoOut> getAllUser(@RequestParam(required = false) List<Long> ids,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size) {
        log.info("Выполняется GET-запрос. Получить всех пользователей.");
        return service.getAllUser(ids, from, size);
    }

    /*
        Удалить подборку
     */
    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Выполняется DELETE-запрос. Удалить подборку.");
        service.deleteCompilation(compId);
    }

    /*
        Удалить категорию
     */
    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCategories(@PathVariable Long catId) {
        log.info("Выполняется DELETE-запрос. Удаление категории id = {}", catId);
        service.removeCategories(catId);
    }

    /*
        Удалить сотрудника
     */
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        log.info("Выполняется DELETE-запрос. Удалить пользователя.");
        service.deleteUser(userId);
    }
}