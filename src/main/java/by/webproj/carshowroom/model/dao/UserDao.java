package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
   /**
    *
    * @param user
    * @return return user with id if added
    * @throws DaoException
    */
   User addUser(User user) throws DaoException;

   /**
    *
    * @param login
    * @return user by login
    * @throws DaoException
    */
   Optional<User> findUserByLogin(String login) throws DaoException;

   /**
    *
    * @return list users
    * @throws DaoException
    */
   List<User> findAll() throws DaoException;

   /**
    *
    * @return list client users
    * @throws DaoException
    */
   List<User> findAllClients() throws DaoException;
}
