package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;

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
            return toReturn;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
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

    public List<?> findAllByClassName(Class className) {
        return tx(session -> session.createQuery("from " + className.getName()).getResultList());
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

    public List<CarBrand> findAllCarBrands() {
        return tx(session -> session
                .createQuery("from CarBrand")
//                .createQuery("select distinct cb from CarBrand cb "
//                        + " join fetch cb.carAds "
//                        + " join fetch cb.carModels")
                .getResultList());
    }

    public List<BodyType> findAllBodyTypes() {
        return tx(session -> session
                .createQuery("from BodyType")
//                .createQuery("select distinct bt from BodyType bt "
//                        + " join fetch bt.carModels as cm "
//                        + " join fetch bt.carAds as ca ")
                .getResultList());
    }

    public List<Transmission> findAllTransmissions() {
        return tx(session -> session
                .createQuery("from Transmission")
//                .createQuery("select distinct tr from Transmission tr "
//                        + " join fetch tr.carModels as cm "
//                        + " join fetch tr.carAds as ca")
                .getResultList());
    }

}
