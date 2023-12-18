package ru.practicum.ewm.server.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.server.model.enums.StateEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
    Сущность таблицы базы данных события
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvents;

    private String annotation; // Краткое описание

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    private Categories category; // Категория

    private Long confirmedRequests; // кол-во одобренных заявок

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn; // Дата создания


    private String description; // Полное описание

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate; // Дата события

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_initiator")
    private User initiator; // Создатель

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_location")
    private Location location; // Координаты

    private Boolean paid; // Платное ли событие

    private Long participantLimit; // Ограничение на количество участников

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn; // Дата и время публикации события

    private Boolean requestModeration; // Нужна ли пре-модерация заявок на участие

    @Enumerated(EnumType.STRING)
    private StateEnum state; // Список состояний жизненного цикла события

    private Long views; // Кол-во просмотрев события

    private String title; // Заголовок
}