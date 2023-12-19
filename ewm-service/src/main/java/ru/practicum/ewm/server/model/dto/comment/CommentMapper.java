package ru.practicum.ewm.server.model.dto.comment;

import ru.practicum.ewm.server.model.entity.Comment;
import ru.practicum.ewm.server.model.entity.Events;
import ru.practicum.ewm.server.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.server.model.dto.events.EventsMapper.eventToEventDtoShort;
import static ru.practicum.ewm.server.model.dto.user.UserMapper.userToUserDtoShort;

/*
    Маппер сущностей комментариев
 */
public class CommentMapper {

    public static Comment coommentDtoInToComment(CommentDtoIn commentDtoIn, Events events, User user) {
        return Comment.builder()
                .comment(commentDtoIn.getComment())
                .event(events)
                .commentator(user)
                .created(LocalDateTime.now())
                .build();
    }

    public static CommentDtoOut commentToCommentDtoOut(Comment comment) {
        return CommentDtoOut.builder()
                .idComment(comment.getIdComment())
                .event(eventToEventDtoShort(comment.getEvent()))
                .comment(comment.getComment())
                .commentator(userToUserDtoShort(comment.getCommentator()))
                .created(comment.getCreated())
                .build();

    }

    public static Comment coomentDtoInMergeComment(Comment comment, CommentDtoIn commentDtoIn) {
        return Comment.builder()
                .idComment(comment.getIdComment())
                .created(comment.getCreated())
                .commentator(comment.getCommentator())
                .event(comment.getEvent())
                .comment(commentDtoIn.getComment())
                .build();
    }

    public static List<CommentDtoOut> allCommentToAllCommentDtoOut(List<Comment> allComment) {
        return allComment.stream().map(CommentMapper::commentToCommentDtoOut).collect(Collectors.toList());
    }
}
