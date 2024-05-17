package ua.karazin.example.service.impl;

import ua.karazin.example.dao.RoleDao;
import ua.karazin.example.dao.UserDao;
import ua.karazin.example.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {

    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserRegistrationService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    private boolean loginExists(String login) {
        return userDao.findByLogin(login) != null;
    }

    @Transactional
    public void registerNewUser(User user) {
        if (loginExists(user.getLogin())) {
            throw new RuntimeException("User with this login exists: " + user.getLogin());
        }
        user.setRole(roleDao.findByName("user"));
        userDao.create(user);
    }
}
