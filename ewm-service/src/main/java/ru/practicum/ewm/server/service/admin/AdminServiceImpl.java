package ru.practicum.ewm.server.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.server.exception.IncorrectlyCreatedRequestExceptions;
import ru.practicum.ewm.server.exception.LackOfDataInDatabaseExceptions;
import ru.practicum.ewm.server.exception.ViolationOfDataUniquenessExceptions;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoIn;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoOut;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoIn;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoOut;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoUpdate;
import ru.practicum.ewm.server.model.dto.events.EventUpdateDtoIn;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;
import ru.practicum.ewm.server.model.dto.user.UserDtoIn;
import ru.practicum.ewm.server.model.dto.user.UserDtoOut;
import ru.practicum.ewm.server.model.entity.Categories;
import ru.practicum.ewm.server.model.entity.Compilation;
import ru.practicum.ewm.server.model.entity.Events;
import ru.practicum.ewm.server.model.enums.StateAdmin;
import ru.practicum.ewm.server.model.enums.StateEnum;
import ru.practicum.ewm.server.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.practicum.ewm.server.model.dto.categories.CategoriesMapper.categoriesDtoInToCategories;
import static ru.practicum.ewm.server.model.dto.categories.CategoriesMapper.categoriesToCategoriesDtoOut;
import static ru.practicum.ewm.server.model.dto.compilations.CompilationMapper.*;
import static ru.practicum.ewm.server.model.dto.events.EventsMapper.*;
import static ru.practicum.ewm.server.model.dto.user.UserMapper.*;
import static ru.practicum.ewm.server.model.enums.StateAdmin.REJECT_EVENT;
import static ru.practicum.ewm.server.model.enums.StateEnum.CANCELED;
import static ru.practicum.ewm.server.model.enums.StateEnum.PUBLISHED;
import static ru.practicum.ewm.server.util.ValidationDate.checkNameCategories;

/*
    Имплементация сервиса для степени доступности ADMIN
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CategoriesRepository categoriesRepository;
    private final EventsRepository eventsRepository;
    private final UserRepository userRepository;
    private final CompilationRepository compilationRepository;
    private final LocationRepository locationRepository;


    @Override
    public CategoriesDtoOut createCategories(CategoriesDtoIn categoriesDtoIn) {
        checkNameCategories(categoriesDtoIn.getName());

        if (categoriesRepository.existsByNameCategories(categoriesDtoIn.getName())) {
            throw new ViolationOfDataUniquenessExceptions(
                    String.format("Имя категории %s уже существует в базе", categoriesDtoIn.getName()));
        }

        return categoriesToCategoriesDtoOut(
                categoriesRepository.save(categoriesDtoInToCategories(categoriesDtoIn))
        );

    }

    @Override
    public void removeCategories(Long id) {
        Categories deleteCategories = categoriesRepository.findById(id).orElseThrow(
                () -> new LackOfDataInDatabaseExceptions(String.format("Пользователь с id %d не найден", id)));


        if (eventsRepository.existsByCategory(deleteCategories)) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Категория %s не может быть удалена, т.к. она еще используется", deleteCategories.getNameCategories()));
        }
        categoriesRepository.delete(deleteCategories);
    }

    @Override
    public CategoriesDtoOut editCategories(Long id, CategoriesDtoIn editCategoriesDtoIn) {
        checkNameCategories(editCategoriesDtoIn.getName());

        if (categoriesRepository.existsByNameCategoriesAndIdCategoriesNot(editCategoriesDtoIn.getName(), id)) {
            throw new ViolationOfDataUniquenessExceptions(
                    String.format("Имя категории %s уже существует в базе", editCategoriesDtoIn.getName()));
        }
        Categories foundCategories = categoriesRepository.findById(id).orElseThrow(
                () -> new LackOfDataInDatabaseExceptions(String.format("Пользователь с id %d не найден", id)));
        foundCategories.setNameCategories(editCategoriesDtoIn.getName());

        return categoriesToCategoriesDtoOut(categoriesRepository.save(foundCategories));
    }

    @Override
    public List<EventsDtoOut> getEventsByFilter(List<Long> users,
                                                List<StateEnum> states,
                                                List<Long> categories,
                                                String rangeStart,
                                                String rangeEnd,
                                                Integer from,
                                                Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start;
        LocalDateTime end;

        if (rangeEnd == null || rangeStart == null) {

            start = rangeStart == null ? LocalDateTime.now() : LocalDateTime.parse(rangeStart, formatter);
            end = rangeEnd == null ? start.plusYears(20) : LocalDateTime.parse(rangeEnd, formatter);
        } else {
            start = LocalDateTime.parse(rangeStart, formatter);
            end = LocalDateTime.parse(rangeEnd, formatter);
        }

        return allEventToEventDtoOut(eventsRepository.findEventsByFilters(users, states, categories, start, end, pageable));
    }

    @Override
    public EventsDtoOut editEvent(Long eventId, EventUpdateDtoIn eventsDtoIn) {
        Events event = eventsRepository.findById(eventId).orElseThrow(); // TODO обработать
        if (eventsDtoIn.getEventDate() != null && eventsDtoIn.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IncorrectlyCreatedRequestExceptions("Дата при изменении события не может быть уже наступившей");
        }

        if (event.getState() == PUBLISHED) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Событие с id %d уже опубликовано", eventId));
        }

        if (event.getState() == CANCELED) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Событие с id %d было отклонено", eventId));
        }

        Categories categories = null;
        if (eventsDtoIn.getCategory() != null) {
            categories = categoriesRepository.findById(eventsDtoIn.getCategory()).orElseThrow();
        }
        if (eventsDtoIn.getStateAction() != null) {
            if (eventsDtoIn.getStateAction().equals(StateAdmin.PUBLISH_EVENT.toString())) {
                eventsDtoIn.setStateAction(PUBLISHED.toString());
            } else if (eventsDtoIn.getStateAction().equals(REJECT_EVENT.toString())) {
                eventsDtoIn.setStateAction(CANCELED.toString());
            } else {
                throw new IncorrectlyCreatedRequestExceptions(String.format(" статуса %s не существует", eventsDtoIn.getStateAction()));
            }
        }

        event = eventsUpdateDtoMergeEvents(eventsDtoIn, event, categories);
        locationRepository.save(event.getLocation());
        event.setPublishedOn(LocalDateTime.now());
        return eventsToEventsDtoOut(eventsRepository.save(event));
    }

    @Override
    public List<UserDtoOut> getAllUser(List<Long> ids, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from / size, size);
        if (ids == null) {
            return allUserToAllUserDtoOut(userRepository.findAll(page).getContent());
        } else {
            return allUserToAllUserDtoOut(userRepository.findByIdUserIn(ids, page));
        }
    }

    @Override
    public UserDtoOut createUser(UserDtoIn userDtoIn) {
        if (userRepository.existsByName(userDtoIn.getName())) {
            throw new ViolationOfDataUniquenessExceptions(String.format("Имя %s уже существует в базе", userDtoIn.getName()));
        }
        return userToUserDtoOut(userRepository.save(userDtoInToUser(userDtoIn)));
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new LackOfDataInDatabaseExceptions(String.format("Пользователя с id = %d не существует", userId));
        }
        userRepository.deleteById(userId);
    }

    @Override
    public CompilationDtoOut createCompilation(CompilationDtoIn compilationDtoIn) {
        Set<Events> allEvents = null;
        if (compilationDtoIn.getEvents() != null) {
            allEvents = eventsRepository.findByIdEventsIn(compilationDtoIn.getEvents());
        }
        return compilationToCompilationDtoOut(compilationRepository.save(compilationDtoInToCompilation(compilationDtoIn, allEvents)));
    }

    @Override
    public CompilationDtoOut updateCompilations(Long compId, CompilationDtoUpdate compilationDtoIn) {
        Set<Events> allEvents = new HashSet<>();
        if (compilationDtoIn.getEvents() != null) {
            allEvents = eventsRepository.findByIdEventsIn(compilationDtoIn.getEvents());
        } else {
            compilationDtoIn.setEvents(new ArrayList<>());
        }
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(); //TODO throws
        Compilation savedCompilation = mergeUpdateCompilationToCompilationDtoIn(compilation, compilationDtoIn, allEvents);
        return compilationToCompilationDtoOut(compilationRepository.save(savedCompilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();//TODO THROW
        compilationRepository.delete(compilation);
    }
}
