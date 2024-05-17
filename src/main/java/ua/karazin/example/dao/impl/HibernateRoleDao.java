package ua.karazin.example.dao.impl;

import ua.karazin.example.dao.RoleDao;
import ua.karazin.example.entities.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateRoleDao implements RoleDao {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateRoleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public HibernateRoleDao() {
    }

    @Override
    public void create(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.save(role);
    }

    @Override
    public void update(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.update(role);
    }

    @Override
    public void remove(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(role);
    }

    @Override
    public Role findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Role where name=:name");
        query.setParameter("name", name);
        return (Role) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Role").list();
    }
}

