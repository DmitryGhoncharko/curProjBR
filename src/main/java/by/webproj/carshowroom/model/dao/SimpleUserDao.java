package by.webproj.carshowroom.model.dao;


import by.webproj.carshowroom.entity.Role;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleUserDao implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserDao.class);
    private static final String SQL_ADD_USER = "insert into user(user_login, user_password, user_role_id) values (?,?,?)";
    private static final String SQL_FIND_USER_BY_LOGIN = "select user_id, user_login, user_password, r.role_name  from  user " +
            "left join role r on r.role_id = user.user_role_id " +
            "where user_login = ?";
    private static final String SQL_FIND_ALL_CLIENTS  = "select user_id, user_login, user_password, r.role_name  from  user" +
            " left join role r on user.user_role_id = r.role_id";
    private final ConnectionPool connectionPool;

    public SimpleUserDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public User addUser(User user) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, 1);
            final int countCreatedRows = preparedStatement.executeUpdate();
            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (countCreatedRows > 0 && generatedKeys.next()) {
                return new User.Builder().
                        withUserId(generatedKeys.getLong(1)).
                        withUserLogin(user.getLogin()).
                        withUserPassword(user.getPassword()).
                        withUserRole(user.getUserRole()).
                        build();
            }
        } catch (SQLException sqlException) {
            LOG.error("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole(), sqlException);
            throw new DaoException("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole());
        }
        LOG.error("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole());
        throw new DaoException("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole());
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User.Builder().
                        withUserId(resultSet.getLong(1)).
                        withUserLogin(resultSet.getString(2)).
                        withUserPassword(resultSet.getString(3)).
                        withUserRole(Role.valueOf(resultSet.getString(4))).
                        build());
            }
        } catch (SQLException sqlException) {
            LOG.error("Cannot find user by login, login: " + login, sqlException);
            throw new DaoException("Cannot find user by login, login: " + login, sqlException);
        }
        LOG.error("Cannot find user by login, login: " + login);
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws DaoException {
        final List<User> users = new ArrayList<>();
        try(final Connection connection = connectionPool.getConnection(); final Statement statement = connection.createStatement()){
            final ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_CLIENTS);
            while (resultSet.next()){
                final User user = new User.Builder().
                        withUserId(resultSet.getLong(1)).
                        withUserLogin(resultSet.getString(2)).
                        withUserPassword(resultSet.getString(3)).
                        withUserRole(Role.valueOf(resultSet.getString(4))).
                        build();
                users.add(user);
            }
        }catch (SQLException e){
            LOG.error("Cannot find users as clients", e);
            throw new DaoException("Cannot find users as clients", e);
        }
        return users;
    }

    @Override
    public List<User> findAllClients() throws DaoException {
        final List<User> users = new ArrayList<>();
        try(final Connection connection = connectionPool.getConnection(); final Statement statement = connection.createStatement()){
            final ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_CLIENTS + " where user_role_id = 1");
            while (resultSet.next()){
                final User user = new User.Builder().
                        withUserId(resultSet.getLong(1)).
                        withUserLogin(resultSet.getString(2)).
                        withUserPassword(resultSet.getString(3)).
                        withUserRole(Role.valueOf(resultSet.getString(4))).
                        build();
                users.add(user);
            }
        }catch (SQLException e){
            LOG.error("Cannot find users as clients", e);
            throw new DaoException("Cannot find users as clients", e);
        }
        return users;
    }
}
