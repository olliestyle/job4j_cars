package ru.job4j.service;

import ru.job4j.model.*;
import ru.job4j.repository.CarAdRepository;

import java.util.List;

public class CarAdService {
    private static final CarAdRepository CAR_AD_REPOSITORY = CarAdRepository.getInstance();

    private CarAdService() {

    }

    private static final class Holder {
        private static final CarAdService CAR_AD_SERVICE = new CarAdService();
    }

    public static CarAdService getInstance() {
        return Holder.CAR_AD_SERVICE;
    }

    public List<CarModel> findAllModelsById(Integer id) {
        return CAR_AD_REPOSITORY.findAllModelsById(id);
    }

    public List<?> findAllByClassName(Class clazz) {
        return CAR_AD_REPOSITORY.findAllByClassName(clazz);
    }

    public List<CarAd> findAllCarAds() {
        return CAR_AD_REPOSITORY.findAllCarAds();
    }
}
