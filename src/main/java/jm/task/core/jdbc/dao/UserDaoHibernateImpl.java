package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Objects;

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
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_CREATE_TABLE, User.class).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            Objects.requireNonNull(transaction).rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_DELETE_TABLE, User.class).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            Objects.requireNonNull(transaction).rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User newUser = new User();
            newUser.setName(name);
            newUser.setLastName(lastName);
            newUser.setAge(age);
            session.save(newUser);
            transaction.commit();
        } catch (RuntimeException e) {
            Objects.requireNonNull(transaction).rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User delUser = session.get(User.class, id);
            session.delete(delUser);
            transaction.commit();
        } catch (RuntimeException e) {
            Objects.requireNonNull(transaction).rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> entityList;
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            entityList = session.createQuery(criteria).getResultList();
            for (User e : entityList) {
                System.out.println(e.toString());
            }
        }
        return entityList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (User e : getAllUsers()) {
                session.delete(e);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            Objects.requireNonNull(transaction).rollback();
        }
    }
}

