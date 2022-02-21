package ru.job4j.repository;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CarAdRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory factory = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private CarAdRepository() {

    }

    private static final class CarAdRepositoryHolder {
        private static final CarAdRepository CAR_AD_REPOSITORY = new CarAdRepository();
    }

    public static CarAdRepository getInstance() {
        return CarAdRepositoryHolder.CAR_AD_REPOSITORY;
    }

    public <T> T tx(Function<Session, T> function) {
        Session session = factory.openSession();
        session.beginTransaction();
        try {
            T toReturn = function.apply(session);
            session.getTransaction().commit();
            return toReturn;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void addPhotosToCarAd(Integer carAdId, List<String> photos) {
        tx(session -> {
            CarAd carAd = session.get(CarAd.class, carAdId);
            System.out.println(carAd.getPhotos());
            photos.forEach(p -> carAd.getPhotos().add(p));
            System.out.println(carAd.getPhotos());
            session.saveOrUpdate(carAd);
            return null;
        });
    }

    public Serializable addNewCarAd(Integer carBrandId, Integer carModelId, Integer bodyTypeId, Integer transmissionId, short year, Integer mileage, BigDecimal price, String desc, User user) {
        return tx(session -> {
            CarBrand carBrand = session.get(CarBrand.class, carBrandId);
            CarModel carModel = session.get(CarModel.class, carModelId);
            BodyType bodyType = session.get(BodyType.class, bodyTypeId);
            Transmission transmission = session.get(Transmission.class, transmissionId);
            User userToAdd = session.get(User.class, user.getId());

            CarAd toAdd = new CarAd.Builder()
                    .carBrand(carBrand)
                    .carModel(carModel)
                    .bodyType(bodyType)
                    .transmission(transmission)
                    .manufactureYear(year)
                    .mileage(mileage)
                    .user(userToAdd)
                    .price(price)
                    .description(desc)
                    .build();

            return session.save(toAdd);
        });
    }

    public List<CarAd> findByUserId(Integer userId) {
        return tx(session -> session.createQuery("select distinct ca from CarAd ca "
                   + " join fetch ca.carModel cm"
                   + " join fetch ca.bodyType bt"
                   + " join fetch ca.carBrand cb"
                   + " join fetch ca.transmission tm"
                   + " join fetch ca.user u"
                   + " where u.id = :userId ", CarAd.class)
                   .setParameter("userId", userId)
                   .getResultList());
    }

    public List<CarAd> findByCarModel(String carModel) {
        return tx(session -> session.createQuery("select distinct ca from CarAd ca "
                + " join fetch ca.carModel cm"
                + " join fetch ca.bodyType bt"
                + " join fetch ca.carBrand cb"
                + " join fetch ca.transmission tm"
                + " join fetch ca.user u"
                + " where cm.carModel = :modelToFind ", CarAd.class)
                .setParameter("modelToFind", carModel)
                .getResultList());
    }

    public List<CarAd> findWithPhoto() {
        return tx(session -> session.createQuery("select distinct ca from CarAd ca "
                + " where ca.photo <> '' ", CarAd.class)
                .getResultList());
    }

    public List<CarAd> findByLastDay() {
        return tx(session -> session.createQuery("from CarAd ca where ca.created >= current_date - 1")
                .getResultList());
    }

    public List<?> findAllByClassName(Class clazz) {
        return tx(session -> session.createQuery("from " + clazz.getName()).getResultList());
    }

    public List<CarModel> findAllModelsById(Integer id) {
        return tx(session -> session
                .createQuery("select distinct cm from CarModel cm "
                        + " join fetch cm.carBrand as cb "
                        + " join fetch cm.bodyType as bt "
                        + " where cb.id = :id")
                .setParameter("id", id)
                .getResultList());
    }

    public List<CarAd> findAllCarAds() {
        return tx(session -> session
                .createQuery("select distinct ca from CarAd ca "
                        + " join fetch ca.user as u "
                        + " join fetch ca.carBrand as cb "
                        + " join fetch ca.carModel as cm "
                        + " join fetch ca.bodyType as bt "
                        + " join fetch ca.transmission as t"
                        + " join fetch ca.photos as p")
                .getResultList());
    }

    public Integer changeCarAdStatus(Integer id) {
        return tx(session -> session.
                createQuery("update CarAd set isSold = :sold where id = :id")
                .setParameter("sold", true)
                .setParameter("id", id)
                .executeUpdate());
    }

    public List<CarAd> findByCrit(int carBrand, int carModel, int bodyType, int transmission) {
        return tx(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CarAd> carAdCriteria = cb.createQuery(CarAd.class);
            Root<CarAd> carAdRoot = carAdCriteria.from(CarAd.class);
            carAdCriteria.select(carAdRoot);
            Predicate predicate = getPredicate(carAdRoot, cb, carBrand, carModel, bodyType, transmission);
            carAdCriteria.where(predicate);
            List<CarAd> toReturn = session.createQuery(carAdCriteria).getResultList();
            System.out.println("hello");
            toReturn.forEach(ca -> {
                ca.setUser((User) Hibernate.unproxy(ca.getUser()));
                ca.setCarBrand((CarBrand) Hibernate.unproxy(ca.getCarBrand()));
                ca.setCarModel((CarModel) Hibernate.unproxy(ca.getCarModel()));
                ca.setBodyType((BodyType) Hibernate.unproxy(ca.getBodyType()));
                ca.setTransmission((Transmission) Hibernate.unproxy(ca.getTransmission()));
                List<String> photos = new ArrayList<>();
                for (String s: ca.getPhotos()) {
                    photos.add(s);
                }
            });
            return toReturn;
        });
    }

    private Predicate getPredicate(Root<CarAd> carAdRoot, CriteriaBuilder cb, int carBrand, int carModel, int bodyType, int transmission) {
        List<Predicate> predicates = new ArrayList<>();
        if (carBrand != 0) {
            predicates.add(cb.equal(carAdRoot.get("carBrand").get("id"), carBrand));
        }
        if (carModel != 0) {
            predicates.add(cb.equal(carAdRoot.get("carModel").get("id"), carModel));
        }
        if (bodyType != 0) {
            predicates.add(cb.equal(carAdRoot.get("bodyType").get("id"), bodyType));
        }
        if (transmission != 0) {
            predicates.add(cb.equal(carAdRoot.get("transmission").get("id"), transmission));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
