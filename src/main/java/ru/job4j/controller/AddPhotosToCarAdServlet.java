package ru.job4j.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.service.CarAdService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AddPhotosToCarAdServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> photos = new ArrayList<>();
        Integer carAdId = Integer.parseInt(req.getParameter("carAdId"));
        InputStream in = AddCarAdServlet.class.getClassLoader().getResourceAsStream("app.properties");
        Properties prop = new Properties();
        prop.load(in);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
            File folder = new File(prop.getProperty("uploadPath"));
            if (!folder.exists()) {
                folder.mkdir();
            }
            int i = 1;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String photo = "" + carAdId + i++;
                    File file = new File(folder + File.separator + photo);
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                    photos.add(photo);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            return;
        }
        CarAdService.getInstance().addPhotosToCarAd(carAdId, photos);
    }
}
