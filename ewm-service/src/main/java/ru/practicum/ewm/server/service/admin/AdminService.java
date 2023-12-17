package ru.practicum.ewm.server.service.admin;

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

import java.util.List;

/*
    Интерфейс сервиса для степени доступности ADMIN
 */
public interface AdminService {

    /*
        Создать новую категорию
     */
    CategoriesDtoOut createCategories(CategoriesDtoIn categoriesDtoIn);

    /*
        Удалить категорию по id
     */
    void removeCategories(Long id);

    /*
        Редактировать категорию
     */
    CategoriesDtoOut editCategories(Long id, CategoriesDtoIn editCategoriesDtoIn);

    /*
        Получить события по заданному фильтру
     */
    List<EventsDtoOut> getEventsByFilter(List<Long> users, List<StateEnum> states, List<Long> categories,
                                         String rangeStart, String rangeEnd, Integer from, Integer size);

    /*
        Изменить событие
     */
    EventsDtoOut editEvent(Long eventId, EventUpdateDtoIn eventsDtoIn);

    /*
        Получить всех сотрудников
     */
    List<UserDtoOut> getAllUser(List<Long> ids, Integer from, Integer size);

    /*
        Создать пользователя
     */
    UserDtoOut createUser(UserDtoIn userDtoIn);

    /*
        Удалить пользователя
     */
    void deleteUser(Long userId);

    /*
        Создать подборку
     */
    CompilationDtoOut createCompilation(CompilationDtoIn compilationDtoIn);

    /*
        Изменить подборку
     */
    CompilationDtoOut updateCompilations(Long compId, CompilationDtoUpdate compilationDtoIn);

    /*
        Удалить подборку
     */
    void deleteCompilation(Long compId);
}
