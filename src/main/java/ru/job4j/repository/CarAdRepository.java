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
}
