package ru.job4j.service;

import ru.job4j.model.*;
import ru.job4j.repository.CarAdRepository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CarAdService {
    private static final CarAdRepository CAR_AD_REPOSITORY = CarAdRepository.getInstance();

    private CarAdService() {

    }

    public void addPhotosToCarAd(Integer carAdId, List<String> photos) {
        CAR_AD_REPOSITORY.addPhotosToCarAd(carAdId, photos);
    }

    private static final class Holder {
        private static final CarAdService CAR_AD_SERVICE = new CarAdService();
    }

    public static CarAdService getInstance() {
        return Holder.CAR_AD_SERVICE;
    }

    public Serializable addNewCarAd(String carBrand, String carModel, String bodyType, String transmission, String year, String mileage, String price, String desc, User user) {
        Integer carBrandId = Integer.parseInt(carBrand);
        Integer carModelId = Integer.parseInt(carModel);
        Integer bodyTypeId = Integer.parseInt(bodyType);
        Integer transmissionId = Integer.parseInt(transmission);
        short yearToAdd = Short.parseShort(year);
        Integer mileageToAdd = Integer.parseInt(mileage);
        BigDecimal priceToAdd = new BigDecimal(Integer.parseInt(price));
        return CAR_AD_REPOSITORY.addNewCarAd(carBrandId, carModelId, bodyTypeId, transmissionId, yearToAdd, mileageToAdd, priceToAdd, desc, user);
    }
    public List<CarAd> findByUserId(Integer userId) {
        return CAR_AD_REPOSITORY.findByUserId(userId);
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

    public Integer changeCarAdStatus(String id) {
        return CAR_AD_REPOSITORY.changeCarAdStatus(Integer.parseInt(id));
    }

    public List<CarAd> findByCrit(int carBrand, int carModel, int bodyType, int transmission) {
        return CAR_AD_REPOSITORY.findByCrit(carBrand, carModel, bodyType, transmission);
    }
}
