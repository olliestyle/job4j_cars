package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.BodyType;
import ru.job4j.model.CarBrand;
import ru.job4j.service.CarAdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BodyTypeServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream outputStream = resp.getOutputStream();
        List<BodyType> bodyTypes = (List<BodyType>) CarAdService.getInstance().findAllByClassName(BodyType.class);
        bodyTypes.forEach(bt -> {
            bt.setCarAds(null);
            bt.setCarModels(null);
        });
        String json = GSON.toJson(bodyTypes);
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
