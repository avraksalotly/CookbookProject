package com.client.logic;
import com.client.dto.DishDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.*;
import java.util.*;

public class ServerConnector {
    private final String URL = "http://localhost:8080/server/api";
    private HttpClient client = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    public boolean auth(String u, String p, String act) {
        try {
            String json = gson.toJson(Map.of("username", u, "password", p, "action", act));
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(URL)).POST(HttpRequest.BodyPublishers.ofString(json)).build();
            return client.send(req, HttpResponse.BodyHandlers.ofString()).body().contains("true");
        } catch (Exception e) { return false; }
    }

    public List<DishDto> search(String type, String query) {
        try {
            String q = query.replace(" ", "%20");
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(URL + "?type=" + type + "&query=" + q)).GET().build();
            String body = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
            return gson.fromJson(body, new TypeToken<List<DishDto>>(){}.getType());
        } catch (Exception e) { return new ArrayList<>(); }
    }
}