package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LogInServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream outputStream = resp.getOutputStream();
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            String message = "UserIsEmpty";
            sendMessage(message, outputStream);
        } else {
            String message = user.getName();
            sendMessage(message, outputStream);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream outputStream = resp.getOutputStream();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User toTest = UserService.getInstance().findByEmail(email);
        if (toTest != null && toTest.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", toTest);
            String message = toTest.getName();
            sendMessage(message, outputStream);
        } else {
            String message = "Не верное имя или пароль";
            sendMessage(message, outputStream);
        }
    }

    private void sendMessage(String message, OutputStream outputStream) throws IOException {
        String json = GSON.toJson(message);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
