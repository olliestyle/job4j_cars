package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.User;
import ru.job4j.service.CarAdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class AddCarAdServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String carBrand = req.getParameter("carBrand");
        String carModel = req.getParameter("carModel");
        String bodyType = req.getParameter("bodyType");
        String transmission = req.getParameter("transmission");
        String year = req.getParameter("year");
        String mileage = req.getParameter("mileage");
        String price = req.getParameter("price");
        String desc = req.getParameter("desc");

        Integer added = (Integer) CarAdService.getInstance()
                .addNewCarAd(carBrand, carModel, bodyType, transmission, year, mileage, price, desc, user);

        resp.setContentType("application/json; charset=utf-8");
        OutputStream outputStream = resp.getOutputStream();
        String json = GSON.toJson(added);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

    }
}
