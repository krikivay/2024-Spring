package ua.karazin.example.service.impl;

import ua.karazin.example.dao.UserDao;
import ua.karazin.example.dao.impl.HibernateUserDao;
import ua.karazin.example.entities.User;
import ua.karazin.example.service.RoleService;
import ua.karazin.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private RoleService roleService;
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(HibernateUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void create(User user) {
        user.setRole(roleService.findByName(user.getRole().getName()));
        userDao.create(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional
    public void remove(User user) {
        user = findByLogin(user.getLogin());
        userDao.remove(user);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
