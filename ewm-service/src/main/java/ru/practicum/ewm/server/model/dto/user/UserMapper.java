package ru.practicum.ewm.server.model.dto.user;

import ru.practicum.ewm.server.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/*
    Маппер сущности пользователя
 */
public class UserMapper {
    public static User userDtoInToUser(UserDtoIn userDtoIn) {
        return User.builder()
                .email(userDtoIn.getEmail())
                .name(userDtoIn.getName())
                .build();
    }

    public static UserDtoOut userToUserDtoOut(User user) {
        return UserDtoOut.builder()
                .id(user.getIdUser())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static List<UserDtoOut> allUserToAllUserDtoOut(List<User> allUser) {
        return allUser.stream().map(UserMapper::userToUserDtoOut).collect(Collectors.toList());
    }

    public static UserDtoShort userToUserDtoShort(User user) {
        return UserDtoShort.builder()
                .id(user.getIdUser())
                .name(user.getName())
                .build();
    }
}
