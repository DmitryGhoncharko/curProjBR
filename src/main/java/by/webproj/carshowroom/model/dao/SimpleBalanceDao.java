package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Balance;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SimpleBalanceDao implements BalanceDao{
    private static final String SQL_ADD = "insert into balance set user_id = ?, balance_count = ?, course_id = ?";
    private static final String SQL_REMOVE = "delete from balance where balance_id = ?";
    private static final String SQL_UPDATE = "update balance set user_id = ?, balance_count = ?, course_id = ? where balance_id = ?";
    private static final String SQL_GET_ALL = "select balance_id, user_id, balance_count, course_id from balance";
    private final ConnectionPool connectionPool;

    @Override
    public Balance add(Long userId, Double count, Long courseId) throws DaoException {
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setLong(1,userId);
            preparedStatement.setDouble(2,count);
            preparedStatement.setLong(3,courseId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
           if(resultSet.next()){
               return Balance.builder().id(resultSet.getLong(1)).build();
           }
        }catch (SQLException e){
            throw new DaoException("Error",e);
        }
        throw new DaoException("Error");
    }

    @Override
    public boolean remove(Long id) throws DaoException {
      try(Connection connection= connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE)){
          preparedStatement.setLong(1,id);
        return preparedStatement.executeUpdate()>0;
      }catch (SQLException e){
          throw new DaoException("error",e);
      }
    }

    @Override
    public boolean update(Long id, Long userId, Double count, Long courseId) throws DaoException {
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setLong(1,userId);
            preparedStatement.setDouble(2,count);
            preparedStatement.setLong(3,courseId);
            preparedStatement.setLong(4,id);
            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            throw new DaoException("error",e);
        }
    }

    @Override
    public List<Balance> getAll() throws DaoException {
       List<Balance> balances = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()){
           ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);
           while (resultSet.next()){
               balances.add(Balance.builder().id(resultSet.getLong(1)).
                       userId(resultSet.getLong(2)).
                       balance(resultSet.getDouble(3)).
                       courseId(resultSet.getLong(4)).build());
           }
       }catch (SQLException e){
               throw new DaoException("error",e);
       }
        return balances;
    }

    @Override
    public List<Balance> getByUserId(Long userId) throws DaoException {
        List<Balance> balances = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL + " where user_id = ?")){
            statement.setLong(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                balances.add(Balance.builder().id(resultSet.getLong(1)).
                        userId(resultSet.getLong(2)).
                        balance(resultSet.getDouble(3)).
                        courseId(resultSet.getLong(4)).build());
            }
        }catch (SQLException e){
            throw new DaoException("error",e);
        }
        return balances;
    }

    @Override
    public Optional<Balance> get(Long id) throws DaoException {
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("select balance_id, user_id, balance_count, course_id from balance where balance_id = ?")){
            preparedStatement.setLong(1,id);
            ResultSet resultSet=  preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(Balance.builder().
                        id(resultSet.getLong(1)).
                        userId(resultSet.getLong(2)).
                        balance(resultSet.getDouble(3)).
                        courseId(resultSet.getLong(4)).
                        build());
            }
        }catch (SQLException e){
            throw new DaoException("error",e);
        }
        return Optional.empty();
    }
}
