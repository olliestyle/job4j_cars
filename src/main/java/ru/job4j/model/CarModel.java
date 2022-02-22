package ru.job4j.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "carModels")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String carModel;

    @OneToMany(mappedBy = "carModel")
    private List<CarAd> carAds = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "bodyType_id")
    private BodyType bodyType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "carBrand_id")
    private CarBrand carBrand;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "carModels_transmissions",
               joinColumns = @JoinColumn(name = "carModel_id"),
               inverseJoinColumns = @JoinColumn(name = "transmission_id"))
    private Set<Transmission> transmissions = new HashSet<>();

    public static CarModel of(String carModel) {
        CarModel model = new CarModel();
        model.carModel = carModel;
        return model;
    }

    public CarModel() {
    }

    public CarModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public List<CarAd> getCarAds() {
        return carAds;
    }

    public void setCarAds(List<CarAd> carAds) {
        this.carAds = carAds;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public Set<Transmission> getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(Set<Transmission> transmissions) {
        this.transmissions = transmissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel1 = (CarModel) o;
        return id == carModel1.id && Objects.equals(carModel, carModel1.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carModel);
    }

    @Override
    public String toString() {
        return "CarModel{" + "id=" + id + ", carModel='" + carModel + "'}'";
    }
}
