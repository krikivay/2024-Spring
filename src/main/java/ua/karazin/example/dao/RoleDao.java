package ua.karazin.example.dao;

import ua.karazin.example.entities.Role;

import java.util.List;

public interface RoleDao {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    List<Role> findAll();
}
