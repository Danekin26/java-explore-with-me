package ru.practicum.ewm.server.model.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.server.model.dto.events.EventDtoShort;
import ru.practicum.ewm.server.model.dto.user.UserDtoShort;

import java.time.LocalDateTime;

/*
    Сущность комментарий на выходе
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoOut {
    private Long idComment;
    private UserDtoShort commentator;
    private EventDtoShort event;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private String comment;
}
