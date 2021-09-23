package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.*;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return sessionFactory.getCurrentSession().createQuery("FROM User").getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {

        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
                "FROM User WHERE car.model = :paramModel and car.series = :paramSeries", User.class);
        query.setParameter("paramModel", model);
        query.setParameter("paramSeries", series);
        List<User> result = query.getResultList();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

}
