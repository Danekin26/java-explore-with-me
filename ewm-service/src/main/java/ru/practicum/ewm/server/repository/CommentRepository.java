package ru.practicum.ewm.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.server.model.entity.Comment;

import java.util.List;

/*
    Репозиторий для взаимодействия с базой данных сущности комментарии
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventIdEvents(Long idEvents);
}
