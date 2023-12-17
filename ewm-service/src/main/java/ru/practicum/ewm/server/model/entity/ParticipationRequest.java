package ru.practicum.ewm.server.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.server.model.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
    Сущность таблицы базы данных событий
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participation_request")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event")
    private Events events;

    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requester")
    private User requester;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}