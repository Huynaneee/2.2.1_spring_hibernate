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
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
      return query.getResultList();
   }

   @Override
   public List<User> getUsersFromCar(Car car) {

      List<User> users = new ArrayList<>();

      String hqlCars = "FROM Car WHERE model = :model AND series = :series";
      TypedQuery<Car> queryCars = sessionFactory.getCurrentSession().createQuery(hqlCars);
      queryCars.setParameter("model", car.getModel());
      queryCars.setParameter("series", car.getSeries());
      List<Car> cars = queryCars.getResultList();

      String hqlUsers = "FROM User WHERE id = :id";

      for (Car carUser: cars) {
         Query query = sessionFactory.getCurrentSession().createQuery(hqlUsers);
         query.setParameter("id", carUser.getId());
         users.add((User) query.getResultList().get(0));
      }
      return users;
   }

}
