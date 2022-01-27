package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "transmissions")
public class Transmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String transmission;

    @ManyToMany(mappedBy = "transmissions")
    private Set<CarModel> carModels = new HashSet<>();

    @OneToMany(mappedBy = "transmission")
    private Set<CarAd> carAds = new HashSet<>();

    public static Transmission of(String transmission) {
        Transmission trans = new Transmission();
        trans.transmission = transmission;
        return trans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Set<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(Set<CarModel> carModels) {
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
        Transmission that = (Transmission) o;
        return id == that.id && Objects.equals(transmission, that.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transmission);
    }

    @Override
    public String toString() {
        return "Transmission{" + "id=" + id + ", transmission='" + transmission + "'}'";
    }
}
