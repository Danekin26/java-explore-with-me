package ru.practicum.ewm.server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.server.model.entity.Compilation;

import java.util.List;

/*
    Репозиторий для взаимодействия с базой данных подборки
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    /*
        Получить подборку, в зависимости от закреплена сборка или нет
     */
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
