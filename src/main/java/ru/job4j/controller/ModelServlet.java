package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.CarModel;
import ru.job4j.service.CarAdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ModelServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream outputStream = resp.getOutputStream();
        String carBrandId = req.getParameter("carBrandId");
        List<CarModel> carModels = CarAdService.getInstance().findAllModelsById(Integer.parseInt(carBrandId));
        carModels.forEach(cm -> {
            cm.setTransmissions(null);
            cm.setCarAds(null);
            cm.setBodyType(null);
            cm.setCarBrand(null);
        });
        String json = GSON.toJson(carModels);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
