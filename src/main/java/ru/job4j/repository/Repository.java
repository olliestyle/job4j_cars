package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

public class Repository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory factory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private Repository() {

    }

    private static final class Holder {
        private static final Repository REPOSITORY = new Repository();
    }

    public static Repository getInstance() {
        return Holder.REPOSITORY;
    }

    public <T> T op(Function<Session, T> function) {
        Session session = factory.openSession();
        session.beginTransaction();
        try {
            T toReturn = function.apply(session);
            session.getTransaction().commit();
            return toReturn;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
