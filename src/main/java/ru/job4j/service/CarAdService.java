package ru.job4j.service;

import ru.job4j.model.BodyType;
import ru.job4j.model.CarBrand;
import ru.job4j.model.CarModel;
import ru.job4j.model.Transmission;
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

    public List<CarBrand> findAllCarBrands() {
        return CAR_AD_REPOSITORY.findAllCarBrands();
    }

    public List<BodyType> findAllBodyTypes() {
        return CAR_AD_REPOSITORY.findAllBodyTypes();
    }

    public List<Transmission> findAllTransmissions() {
        return CAR_AD_REPOSITORY.findAllTransmissions();
    }
}
