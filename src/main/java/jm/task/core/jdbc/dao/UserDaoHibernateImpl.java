package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50), lastName VARCHAR(50), " +
            "age TINYINT)";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS users";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createNativeQuery(SQL_CREATE_TABLE, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            Util.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            Util.getSessionFactory().getCurrentSession().close();
        }
    }

    @Override
    public void dropUsersTable() {
       Session session;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createNativeQuery(SQL_DELETE_TABLE, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            Util.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            Util.getSessionFactory().getCurrentSession().close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User newUser = new User();
            newUser.setName(name);
            newUser.setLastName(lastName);
            newUser.setAge(age);
            session.save(newUser);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            Util.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            Util.getSessionFactory().getCurrentSession().close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User delUser = session.get(User.class, id);
            session.delete(delUser);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            Util.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            Util.getSessionFactory().getCurrentSession().close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> entityList;
        Session session;
        try {
            session = Util.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            entityList = session.createQuery(criteria).getResultList();
            for (User e : entityList) {
                System.out.println(e.toString());
            }
        } finally {
            Util.getSessionFactory().getCurrentSession().close();
        }
        return entityList;
    }

    @Override
    public void cleanUsersTable() {
        Session session;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            for (User e : getAllUsers()) {
                session.delete(e);
            }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            Util.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            Util.getSessionFactory().getCurrentSession().close();
        }
    }
}

