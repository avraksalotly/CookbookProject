package com.cook.controller;

import com.cook.dto.AuthRequest;
import com.cook.service.CoreService;
import com.cook.domain.User;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/*")
public class MainServlet extends HttpServlet {
    private CoreService core = new CoreService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuthRequest request = gson.fromJson(req.getReader(), AuthRequest.class);

        User currentUser = new User(request.username, request.password);

        if ("reg".equals(request.action)) {
            core.register(currentUser);
        }

        boolean ok = core.login(currentUser);

        resp.getWriter().write(gson.toJson(Map.of("success", ok)));
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        String type = req.getParameter("type");
        String query = req.getParameter("query");
        resp.getWriter().write(gson.toJson(core.search(type, query)));
    }
}