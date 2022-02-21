package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.CarAd;
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
import java.util.List;

public class ChangeStatusServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream outputStream = resp.getOutputStream();
        Integer userId = ((User) req.getSession().getAttribute("user")).getId();
        System.out.println(userId);
        List<CarAd> carAds = CarAdService.getInstance().findByUserId(userId);
        carAds.forEach(ca -> {
            ca.getBodyType().setCarModels(null);
            ca.getBodyType().setCarAds(null);
            ca.getCarBrand().setCarModels(null);
            ca.getCarBrand().setCarAds(null);
            ca.getCarModel().setCarAds(null);
            ca.getCarModel().setTransmissions(null);
            ca.getCarModel().setCarBrand(null);
            ca.getCarModel().setBodyType(null);
            ca.getUser().setCarAdsList(null);
            ca.getUser().setEmail(null);
            ca.getUser().setPassword(null);
            ca.getTransmission().setCarAds(null);
            ca.getTransmission().setCarModels(null);
            ca.setPhotos(null);
        });
        String json = GSON.toJson(carAds);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adId = req.getParameter("adId");
        CarAdService.getInstance().changeCarAdStatus(adId);
    }
}
