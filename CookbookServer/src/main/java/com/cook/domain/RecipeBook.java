package com.cook.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeBook {
    private List<Dish> dishes;

    public RecipeBook(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getAllDishes() {
        return new ArrayList<>(dishes);
    }

    // Логика фильтрации (Java Stream API)
    public List<Dish> searchByName(String query) {
        String q = query.toLowerCase();
        return dishes.stream()
                .filter(d -> d.getName().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Dish> searchByCategory(String query) {
        String q = query.toLowerCase();
        return dishes.stream()
                .filter(d -> d.getCategory().getName().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Dish> searchByIngredient(String query) {
        String q = query.toLowerCase();
        return dishes.stream()
                .filter(d -> d.getIngredients().stream()
                        .anyMatch(i -> i.getName().toLowerCase().contains(q)))
                .collect(Collectors.toList());
    }
}