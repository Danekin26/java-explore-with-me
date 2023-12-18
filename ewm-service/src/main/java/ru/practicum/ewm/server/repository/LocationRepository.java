package ru.practicum.ewm.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.server.model.entity.Location;

/*
    Репозиторий для взаимодействия с базой данных сущности место положения
 */
public interface LocationRepository extends JpaRepository<Location, Long> {
}
