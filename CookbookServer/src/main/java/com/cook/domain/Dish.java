package com.cook.domain;
import java.util.List;

public class Dish {
    private String name;
    private String recipe;
    private Category category;
    private List<Ingredient> ingredients;

    public Dish(String name, String recipe, Category category, List<Ingredient> ingredients) {
        this.name = name; this.recipe = recipe; this.category = category; this.ingredients = ingredients;
    }

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public String getRecipe() { return recipe; }
}