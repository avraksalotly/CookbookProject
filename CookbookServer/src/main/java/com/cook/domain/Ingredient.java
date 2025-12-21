package com.cook.domain;

public class Ingredient {
    private String name;
    private String quantity;
    public Ingredient(String name, String quantity) { this.name = name; this.quantity = quantity; }
    public String getName() { return name; }
    @Override public String toString() { return name + " (" + quantity + ")"; }
}