package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.User;

import java.util.List;
import java.util.function.Function;

public class UserRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory factory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private UserRepository() {

    }

    private static final class Holder {
        private static final UserRepository USER_REPOSITORY = new UserRepository();
    }

    public static UserRepository getInstance() {
        return Holder.USER_REPOSITORY;
    }

    private <T> T op(Function<Session, T> function) {
        Session session = factory.openSession();
        session.beginTransaction();
        try {
            T toReturn = function.apply(session);
            return toReturn;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public User addUser(User user) {
        return op(session -> {
            session.persist(user);
            return user;
        });
    }

    public User findByEmail(String email) {
        return op(session -> session.createQuery("select user from User user where user.email = :email", User.class)
        .setParameter("email", email)
        .uniqueResult());
    }
}
