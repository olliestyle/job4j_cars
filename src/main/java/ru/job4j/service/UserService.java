package ru.job4j.service;

import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

public class UserService {

    private static final UserRepository USER_REPOSITORY = UserRepository.getInstance();

    private UserService() {

    }

    private static final class Holder {
        private static final UserService USER_SERVICE = new UserService();
    }

    public static UserService getInstance() {
        return Holder.USER_SERVICE;
    }

    public User addUser(User user) {
        return USER_REPOSITORY.addUser(user);
    }

    public User findByEmail(String email) {
        return USER_REPOSITORY.findByEmail(email);
    }
}
