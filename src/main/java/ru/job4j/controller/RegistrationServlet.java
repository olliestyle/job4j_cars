package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegistrationServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String email = req.getParameter("email");
        if (UserService.getInstance().findByEmail(email) != null) {
            String message = "Пользователь с таким именем уже зарегистрирован";
            sendMessage(message, output);
        } else {
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            UserService.getInstance().addUser(User.of(name, email, password));
            String message = "Пользователь добавлен";
            sendMessage(message, output);
        }
    }

    private void sendMessage(String message, OutputStream outputStream) throws IOException {
        String json = GSON.toJson(message);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
