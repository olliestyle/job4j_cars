package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.CarAd;
import ru.job4j.service.CarAdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AllAdsServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream outputStream = resp.getOutputStream();
        List<CarAd> carAds = CarAdService.getInstance().findAllCarAds();
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
        });
        String json = GSON.toJson(carAds);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
