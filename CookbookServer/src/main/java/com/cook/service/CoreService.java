package com.cook.service;

import com.cook.dao.Repository;
import com.cook.domain.*;
import com.cook.dto.DishDto;
import java.util.List;
import java.util.stream.Collectors;

public class CoreService {
    private Repository repo = new Repository();

    public boolean login(User user) {
        if (!user.isValid()) return false;
        return repo.checkUser(user);
    }

    public void register(User user) {
        if (user.isValid() && !login(user)) {
            repo.registerUser(user);
        }
    }

    public List<DishDto> search(String type, String query) {
        RecipeBook book = repo.loadRecipeBook();

        List<Dish> result;
        switch (type) {
            case "name": result = book.searchByName(query); break;
            case "category": result = book.searchByCategory(query); break;
            case "ingredient": result = book.searchByIngredient(query); break;
            default: result = book.getAllDishes(); break;
        }

        return result.stream().map(this::toDto).collect(Collectors.toList());
    }

    private DishDto toDto(Dish d) {
        List<String> ings = d.getIngredients().stream().map(Ingredient::toString).collect(Collectors.toList());
        return new DishDto(d.getName(), d.getRecipe(), d.getCategory().getName(), ings);
    }
}