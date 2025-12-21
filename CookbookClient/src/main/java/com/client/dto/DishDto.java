package com.client.dto;

import java.util.List;

public class DishDto {
    public String name;
    public String recipe;
    public String category;
    public List<String> ingredients;

    public DishDto(String name, String recipe, String category, List<String> ingredients) {
        this.name = name; this.recipe = recipe; this.category = category; this.ingredients = ingredients;
    }
}
