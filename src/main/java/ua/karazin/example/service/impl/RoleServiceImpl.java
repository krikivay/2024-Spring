package ua.karazin.example.service.impl;

import ua.karazin.example.dao.RoleDao;
import ua.karazin.example.dao.impl.HibernateRoleDao;
import ua.karazin.example.entities.Role;
import ua.karazin.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(HibernateRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void create(Role role) {
        roleDao.create(role);
    }

    @Override
    @Transactional
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    @Transactional
    public void remove(Role role) {
        roleDao.remove(role);
    }

    @Override
    @Transactional
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    @Transactional
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
