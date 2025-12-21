package com.cook.dao;
import com.cook.domain.*;
import java.sql.*;
import java.util.*;

public class Repository {
    private static Properties sql = new Properties();
    private final String URL = "jdbc:postgresql://localhost:5432/cookbook";
    private final String USER = "postgres";
    private final String PASS = "5301";

    static {
        try {
            sql.load(Repository.class.getClassLoader().getResourceAsStream("queries.properties"));
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public boolean checkUser(User user) {
        try (Connection c = getConn();
             PreparedStatement ps = c.prepareStatement(sql.getProperty("user.find"))) {

            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            return rs.next() && rs.getString("password").equals(user.getPassword());
        } catch (Exception e) { return false; }
    }

    public void registerUser(User user) {
        try (Connection c = getConn();
             PreparedStatement ps = c.prepareStatement(sql.getProperty("user.register"))) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Ошибка регистрации: " + e.getMessage());
        }
    }

    public RecipeBook loadRecipeBook() {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = getConn(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery(sql.getProperty("dish.all"));

            while(rs.next()) {
                int id = rs.getInt("id");

                Category cat = new Category(rs.getString("c_name"));

                List<Ingredient> ingredients = getIngredients(id, c);

                Dish dish = new Dish(
                        rs.getString("name"),
                        rs.getString("recipe"),
                        cat,
                        ingredients
                );
                dishes.add(dish);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return new RecipeBook(dishes);
    }

    private List<Ingredient> getIngredients(int dishId, Connection c) throws SQLException {
        List<Ingredient> list = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql.getProperty("ing.by_dish"))) {
            ps.setInt(1, dishId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Ingredient(rs.getString("name"), rs.getString("quantity")));
            }
        }
        return list;
    }
}