package ru.practicum.ewm.client;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.dto.StatsDtoIn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StateClient {
    private final String statServerUrl = "http://stats-server:9090";

    //private final String statServerUrl = "http://localhost:9090"; ПРИ ЗАПУСКЕ С IDEA
    public final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RestTemplate restTemplate = new RestTemplate();

    public void saveStat(StatsDtoIn statsDtoIn) {
        HttpEntity<StatsDtoIn> httpEntity = new HttpEntity<>(statsDtoIn, defaultHeaders());
        restTemplate.exchange(statServerUrl + "/hit", HttpMethod.POST, httpEntity, Object.class);
    }

    public ResponseEntity<Object> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        Map<String, Object> param = Map.of("start",
                start.format(dateTimeFormatter),
                "end", end.format(dateTimeFormatter),
                "unique", unique);

        String uri = statServerUrl + buildURI(uris);

        return restTemplate.exchange(uri,
                HttpMethod.GET,
                new HttpEntity<>(defaultHeaders()),
                Object.class, param);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders head = new HttpHeaders();
        head.setContentType(MediaType.APPLICATION_JSON);
        head.setAccept(List.of(MediaType.APPLICATION_JSON));
        return head;
    }

    private String buildURI(List<String> uris) {
        StringBuilder url = new StringBuilder();
        url.append("/stats?start={start}&end={end}");
        for (String uri : uris) {
            url.append(String.format("&uris=%s", uri));
        }
        url.append("&unique={unique}");
        return url.toString();
    }
}