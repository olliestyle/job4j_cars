package ru.job4j.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "bodyTypes")
public class BodyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bodyType;

    @OneToMany(mappedBy = "bodyType")
    private List<CarModel> carModels = new ArrayList<>();

    @OneToMany(mappedBy = "bodyType")
    private Set<CarAd> carAds = new HashSet<>();

    public static BodyType of(String bodyType) {
        BodyType body = new BodyType();
        body.bodyType = bodyType;
        return body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public List<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(List<CarModel> carModels) {
        this.carModels = carModels;
    }

    public Set<CarAd> getCarAds() {
        return carAds;
    }

    public void setCarAds(Set<CarAd> carAds) {
        this.carAds = carAds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyType bodyType1 = (BodyType) o;
        return id == bodyType1.id && Objects.equals(bodyType, bodyType1.bodyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bodyType);
    }

    @Override
    public String toString() {
        return "BodyType{" + "id=" + id + ", bodyType='" + bodyType + "'}'";
    }
}
