package ru.practicum.ewm.server.service.publice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.StateClient;
import ru.practicum.ewm.dto.StatsDtoIn;
import ru.practicum.ewm.dto.StatsDtoOut;
import ru.practicum.ewm.server.exception.IncorrectlyCreatedRequestExceptions;
import ru.practicum.ewm.server.exception.InvalidDataEnteredException;
import ru.practicum.ewm.server.exception.LackOfDataInDatabaseExceptions;
import ru.practicum.ewm.server.model.dto.categories.CategoriesDtoOut;
import ru.practicum.ewm.server.model.dto.compilations.CompilationDtoOut;
import ru.practicum.ewm.server.model.dto.events.EventsDtoOut;
import ru.practicum.ewm.server.model.entity.Categories;
import ru.practicum.ewm.server.model.entity.Compilation;
import ru.practicum.ewm.server.model.entity.Events;
import ru.practicum.ewm.server.repository.CategoriesRepository;
import ru.practicum.ewm.server.repository.CompilationRepository;
import ru.practicum.ewm.server.repository.EventsRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static ru.practicum.ewm.server.model.dto.categories.CategoriesMapper.categoriesToCategoriesDtoOut;
import static ru.practicum.ewm.server.model.dto.categories.CategoriesMapper.categoriesToCategoriesDtoOutList;
import static ru.practicum.ewm.server.model.dto.compilations.CompilationMapper.compilationDtoOutList;
import static ru.practicum.ewm.server.model.dto.compilations.CompilationMapper.compilationToCompilationDtoOut;
import static ru.practicum.ewm.server.model.dto.events.EventsMapper.allEventToEventDtoOut;
import static ru.practicum.ewm.server.model.dto.events.EventsMapper.eventsToEventsDtoOut;
import static ru.practicum.ewm.server.model.enums.StateEnum.PUBLISHED;

/*
    Имплементация сервиса для степени доступности PUBLIC
 */
@Service
@RequiredArgsConstructor
@Import(StateClient.class)
public class PublicServiceImpl implements PublicService {
    private final CompilationRepository compilationRepository;
    private final CategoriesRepository categoriesRepository;
    private final EventsRepository eventsRepository;
    private final ObjectMapper objectMapper;


    private final StateClient stateClient;
    private final String app = "ewm-service";

    public List<CompilationDtoOut> getSelectionEvents(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return compilationDtoOutList(compilationRepository.findAllByPinned(pinned, pageable));
    }

    public CompilationDtoOut getSelectionEventById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new LackOfDataInDatabaseExceptions(String.format("Подборка с id = %d не существует", compId)));
        return compilationToCompilationDtoOut(compilation);
    }

    public List<CategoriesDtoOut> getAllCategories(Integer from, Integer size) {
        if (from < 0 || size < 1) throw new InvalidDataEnteredException("size и/или from введены не корректно");
        Pageable pageable = PageRequest.of(from / size, size);
        return categoriesToCategoriesDtoOutList(categoriesRepository.getAll(pageable));
    }

    public CategoriesDtoOut getCategories(Long catId) {
        Categories categories = categoriesRepository.findById(catId).orElseThrow(
                () -> new LackOfDataInDatabaseExceptions(String.format("Категория с id = %d не существует", catId)));
        return categoriesToCategoriesDtoOut(categories);
    }

    public List<EventsDtoOut> getEventsByFilter(String text,
                                                List<Long> categories,
                                                Boolean paid,
                                                String rangeEnd,
                                                String rangeStart,
                                                Boolean onlyAvailable,
                                                String sort,
                                                Integer from,
                                                Integer size,
                                                HttpServletRequest request) {

        if (from < 0 || size < 1) throw new InvalidDataEnteredException("size и/или from введены не корректно");
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

        if (start.isAfter(end)) {
            throw new IncorrectlyCreatedRequestExceptions("Начало диапазона не может быть после окончания при поиске по фильтрам");
        }
        StatsDtoIn statsDtoIn = StatsDtoIn.builder()
                .app(app)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();

        stateClient.saveStat(statsDtoIn);

        return allEventToEventDtoOut(eventsRepository
                .findEventsByFilters(text, categories, paid, start, end, onlyAvailable, sort, pageable));
    }

    public EventsDtoOut getEventById(Long idEvent, HttpServletRequest request) {
        Events events = eventsRepository.findById(idEvent).orElseThrow(() -> new LackOfDataInDatabaseExceptions(String.format("Событие с id = %d недоступно", idEvent)));
        if (!events.getState().equals(PUBLISHED))
            throw new LackOfDataInDatabaseExceptions(String.format("Событие с id = %d недоступно", idEvent));
        StatsDtoIn statsDtoIn = StatsDtoIn.builder()
                .app(app)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        stateClient.saveStat(statsDtoIn);
        events.setViews(getViewByEvents(events));
        return eventsToEventsDtoOut(events);
    }

    private Long getViewByEvents(Events events) {
        ResponseEntity<Object> response = stateClient.getStat(events.getCreatedOn(), LocalDateTime.now(), Collections.singletonList(String.format("/events/%s", events.getIdEvents())), false);
        List<StatsDtoOut> viewStatsList = objectMapper.convertValue(response.getBody(), new TypeReference<>() {
        });
        return (long) viewStatsList.size();
    }
}
