package ru.job4j.repository;

import ru.job4j.model.User;

public class UserRepository {

    private final Repository repository = Repository.getInstance();

    private UserRepository() {

    }

    private static final class Holder {
        private static final UserRepository USER_REPOSITORY = new UserRepository();
    }

    public static UserRepository getInstance() {
        return Holder.USER_REPOSITORY;
    }

    public User addUser(User user) {
        return repository.op(session -> {
            session.persist(user);
            return user;
        });
    }

    public User findByEmail(String email) {
        return repository.op(session -> session.createQuery("select user from User user where user.email = :email", User.class)
        .setParameter("email", email)
        .uniqueResult());
    }
}
