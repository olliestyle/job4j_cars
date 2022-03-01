package ru.job4j.repository;

import org.hibernate.Hibernate;
import ru.job4j.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarAdRepository {

    private final Repository repository = Repository.getInstance();

    private CarAdRepository() {

    }

    private static final class CarAdRepositoryHolder {
        private static final CarAdRepository CAR_AD_REPOSITORY = new CarAdRepository();
    }

    public static CarAdRepository getInstance() {
        return CarAdRepositoryHolder.CAR_AD_REPOSITORY;
    }

    public void addPhotosToCarAd(Integer carAdId, List<String> photos) {
        repository.op(session -> {
            CarAd carAd = session.get(CarAd.class, carAdId);
            photos.forEach(p -> carAd.getPhotos().add(p));
            session.saveOrUpdate(carAd);
            return null;
        });
    }

    public Serializable addNewCarAd(CarAd carAd) {
        return repository.op(session -> session.save(carAd));
    }

    public List<CarAd> findByUserId(Integer userId) {
        return repository.op(session -> session.createQuery("select distinct ca from CarAd ca "
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
        return repository.op(session -> session.createQuery("select distinct ca from CarAd ca "
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
        return repository.op(session -> session.createQuery("select distinct ca from CarAd ca "
                + " where ca.photo <> '' ", CarAd.class)
                .getResultList());
    }

    public List<CarAd> findByLastDay() {
        return repository.op(session -> session.createQuery("from CarAd ca where ca.created >= current_date - 1")
                .getResultList());
    }

    public List<?> findAllByClassName(Class clazz) {
        return repository.op(session -> session.createQuery("from " + clazz.getName()).getResultList());
    }

    public List<CarModel> findAllModelsById(Integer id) {
        return repository.op(session -> session
                .createQuery("select distinct cm from CarModel cm "
                        + " join fetch cm.carBrand as cb "
                        + " join fetch cm.bodyType as bt "
                        + " where cb.id = :id")
                .setParameter("id", id)
                .getResultList());
    }

    public List<CarAd> findAllCarAds() {
        return repository.op(session -> session
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
        return repository.op(session -> session.
                createQuery("update CarAd set isSold = :sold where id = :id")
                .setParameter("sold", true)
                .setParameter("id", id)
                .executeUpdate());
    }

    public List<CarAd> findByCrit(int carBrand, int carModel, int bodyType, int transmission) {
        return repository.op(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CarAd> carAdCriteria = cb.createQuery(CarAd.class);
            Root<CarAd> carAdRoot = carAdCriteria.from(CarAd.class);
            carAdCriteria.select(carAdRoot);
            Predicate predicate = getPredicate(carAdRoot, cb, carBrand, carModel, bodyType, transmission);
            carAdCriteria.where(predicate);
            List<CarAd> toReturn = session.createQuery(carAdCriteria).getResultList();
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
