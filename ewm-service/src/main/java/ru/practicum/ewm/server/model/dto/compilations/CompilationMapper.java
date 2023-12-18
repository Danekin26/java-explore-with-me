package ru.practicum.ewm.server.model.dto.compilations;

import ru.practicum.ewm.server.model.entity.Compilation;
import ru.practicum.ewm.server.model.entity.Events;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.server.model.dto.events.EventsMapper.allEventsToAllEventsDtoShort;

/*
    Маппер сущностей подборки
 */
public class CompilationMapper {
    public static CompilationDtoOut compilationToCompilationDtoOut(Compilation compilation) {
        return CompilationDtoOut.builder()
                .id(compilation.getIdCompilation())
                .events(compilation.getEvents() == null ? null : allEventsToAllEventsDtoShort(compilation.getEvents()))
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static Compilation compilationDtoInToCompilation(CompilationDtoIn compilationDtoIn, Set<Events> eventsSet) {
        return Compilation.builder()
                .events(eventsSet)
                .pinned(compilationDtoIn.getPinned())
                .title(compilationDtoIn.getTitle())
                .build();
    }

    public static List<CompilationDtoOut> compilationDtoOutList(List<Compilation> allCompilation) {
        return allCompilation.stream().map(CompilationMapper::compilationToCompilationDtoOut).collect(Collectors.toList());
    }


    public static Compilation compilationDtoUpdateToCompilation(CompilationDtoUpdate compilationDtoUpdate, Set<Events> eventsSet) {
        return Compilation.builder()
                .events(eventsSet)
                .pinned(compilationDtoUpdate.getPinned())
                .title(compilationDtoUpdate.getTitle())
                .build();
    }

    public static Compilation mergeUpdateCompilationToCompilationDtoIn(Compilation compilation, CompilationDtoUpdate compilationDtoUpdate, Set<Events> events) {
        return Compilation.builder()
                .idCompilation(compilation.getIdCompilation())
                .events(compilationDtoUpdate.getEvents().isEmpty() ? compilation.getEvents() : events)
                .pinned(compilationDtoUpdate.getPinned() == null ? compilation.getPinned() : compilationDtoUpdate.getPinned())
                .title(compilationDtoUpdate.getTitle() == null ? compilation.getTitle() : compilationDtoUpdate.getTitle())
                .build();

    }
}
