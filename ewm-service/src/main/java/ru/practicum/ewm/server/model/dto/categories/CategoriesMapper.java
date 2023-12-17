package ru.practicum.ewm.server.model.dto.categories;

import ru.practicum.ewm.server.model.entity.Categories;

import java.util.List;
import java.util.stream.Collectors;

/*
    Маппер сущности категории
 */
public class CategoriesMapper {
    public static Categories categoriesDtoInToCategories(CategoriesDtoIn categoriesDtoIn) {
        return Categories.builder()
                .nameCategories(categoriesDtoIn.getName())
                .build();
    }

    public static CategoriesDtoOut categoriesToCategoriesDtoOut(Categories categories) {
        return CategoriesDtoOut.builder()
                .id(categories.getIdCategories())
                .name(categories.getNameCategories())
                .build();
    }

    public static List<CategoriesDtoOut> categoriesToCategoriesDtoOutList(List<Categories> allCategories) {
        return allCategories.stream().map(CategoriesMapper::categoriesToCategoriesDtoOut).collect(Collectors.toList());
    }
}
