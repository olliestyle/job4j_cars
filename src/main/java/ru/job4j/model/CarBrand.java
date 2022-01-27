package ru.job4j.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carBrands")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String carBrand;

    @OneToMany(mappedBy = "carBrand")
    private List<CarAd> carAds = new ArrayList<>();

    @OneToMany(mappedBy = "carBrand")
    private List<CarModel> carModels = new ArrayList<>();

    public static CarBrand of(String carBrand) {
        CarBrand brand = new CarBrand();
        brand.carBrand = carBrand;
        return brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public List<CarAd> getCarAds() {
        return carAds;
    }

    public void setCarAds(List<CarAd> carAds) {
        this.carAds = carAds;
    }

    public List<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(List<CarModel> carModels) {
        this.carModels = carModels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBrand carBrand = (CarBrand) o;
        return id == carBrand.id && Objects.equals(this.carBrand, carBrand.carBrand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carBrand);
    }

    @Override
    public String toString() {
        return "CarBrand{" + "id=" + id + ", brand='" + carBrand + "'}'";
    }
}
