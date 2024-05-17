package ua.karazin.example.dao;

import ua.karazin.example.entities.User;

import java.util.List;

public interface UserDao {
    void create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    User findByLogin(String login);

    User findById(Long id);

    User findByEmail(String email);
}
