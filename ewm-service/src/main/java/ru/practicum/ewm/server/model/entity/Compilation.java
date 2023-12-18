package ru.practicum.ewm.server.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/*
    Сущность таблицы базы данных подборки
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompilation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compilation_event",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ToString.Exclude
    private Set<Events> events;

    private Boolean pinned;
    private String title;
}
