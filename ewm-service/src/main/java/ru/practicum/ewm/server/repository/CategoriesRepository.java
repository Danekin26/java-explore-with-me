package ru.practicum.ewm.server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.server.model.entity.Categories;

import java.util.List;

/*
    Репозиторий для взаимодействия с базой данных сущности категории
 */
@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    /*
        Проверить наличие категории по имени и id
     */
    boolean existsByNameCategoriesAndIdCategoriesNot(String nameCategories, Long idCategories);

    /*
        Проверить наличие категории по имени
     */
    boolean existsByNameCategories(String nameCategories);

    /*
        Получить все категории по фильтру
     */
    @Query("select c from Categories AS c ORDER BY c.idCategories ASC ")
    List<Categories> getAll(Pageable pageable);

}
