package ru.practicum.server.controller;

import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.StatsDtoIn;
import ru.practicum.dto.StatsDtoOut;
import ru.practicum.server.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/*
    Контроллера для управления статистикой
 */

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    /*
        Создать объект статистики
     */
    @PostMapping("/hit")
    public void saveStat(@RequestBody @Valid StatsDtoIn statsDtoIn) {
        service.saveStat(statsDtoIn);
    }

    /*
        Получить объект статистики
     */
    @GetMapping("/stats")
    public List<StatsDtoOut> getStats(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                      @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                      @RequestParam(name = "uris", defaultValue = "") @Nullable List<String> uris,
                                      @RequestParam(name = "unique", defaultValue = "false") @DefaultValue("false") Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}