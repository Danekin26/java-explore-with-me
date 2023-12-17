package ru.practicum.ewm.server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.server.model.entity.User;

import java.util.List;

/*
    Репозиторий для работы с базой данных пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*
        Получить пользователей по их id и с ограничением
     */
    List<User> findByIdUserIn(List<Long> ids, Pageable pageable);

    /*
        Проверить наличие пользователя по имени
     */
    boolean existsByName(String name);
}
