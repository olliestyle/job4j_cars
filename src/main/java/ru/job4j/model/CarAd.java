package ru.job4j.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "carAds")
public class CarAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "carBrand_id")
    private CarBrand carBrand;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "carModel_id")
    private CarModel carModel;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "bodyType_id")
    private BodyType bodyType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    private String description;

    private BigDecimal price;

    private String photo;

    private boolean isSold;

    private short manufactureYear;

    private int mileage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public static class Builder {
        private CarAd carAd;
        public Builder() {
            carAd = new CarAd();
        }

        public Builder user(User user) {
            carAd.user = user;
            return this;
        }

        public Builder carBrand(CarBrand carBrand) {
            carAd.carBrand = carBrand;
            return this;
        }

        public Builder carModel(CarModel carModel) {
            carAd.carModel = carModel;
            return this;
        }

        public Builder bodyType(BodyType bodyType) {
            carAd.bodyType = bodyType;
            return this;
        }

        public Builder transmission(Transmission transmission) {
            carAd.transmission = transmission;
            return this;
        }

        public Builder description(String description) {
            carAd.description = description;
            return this;
        }

        public Builder price(BigDecimal price) {
            carAd.price = price;
            return this;
        }

        public Builder photo(String photo) {
            carAd.photo = photo;
            return this;
        }

        public Builder manufactureYear(short manufactureYear) {
            carAd.manufactureYear = manufactureYear;
            return this;
        }

        public Builder mileage(int mileage) {
            carAd.mileage = mileage;
            return this;
        }

        public CarAd build() {
            carAd.created = Date.from(Instant.now());
            return carAd;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public short getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(short manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarAd carAd = (CarAd) o;
        return id == carAd.id && Objects.equals(description, carAd.description) && Objects.equals(photo, carAd.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, photo);
    }

    @Override
    public String toString() {
        return "CarAd{" + "id=" + id + ", user=" + user + ", carBrand=" + carBrand
                + ", carModel=" + carModel + ", bodyType=" + bodyType + ", transmission=" + transmission
                + ", description='" + description + '\'' + ", price=" + price + ", photo='" + photo + '\''
                + ", isSold=" + isSold + ", manufactureYear=" + manufactureYear + ", mileage=" + mileage + "'}'";
    }
}
