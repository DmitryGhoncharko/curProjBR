package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Course;
import by.webproj.carshowroom.entity.Data;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class SimpleDataDao implements DataDao{
    private static final String SQL_ADD = "insert into data(data_course,data_to,data_from) values(?,?,?)";

    private static final String SQL_REMOVE = "delete from data where data_id = ?";

    private static final String SQL_UPDATE = "update data set data_course = ?, data_to = ?, data_from = ? where data_id = ?";

    private static final String SQL_GET_ALL = "select data_id, data_course , c.course_id, c.course_name, c2.course_id, c2.course_name from data " +
            "join course c on c.course_id = data.data_from " +
            "join course c2 on c2.course_id = data.data_to";
    private final ConnectionPool connectionPool;
    @Override
    public Data add(String course, Long toId, Long fromId) throws DaoException {
        try(Connection connection= connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,course);
            preparedStatement.setLong(2,toId);
            preparedStatement.setLong(3,fromId);
            if(preparedStatement.executeUpdate()>0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
               if(resultSet.next()){
                   return  Data.builder().id(resultSet.getLong(1)).
                           course(Integer.valueOf(course)).
                           to(Course.builder().id(toId).build()).
                           from(Course.builder().id(fromId).build()).
                           build();
               }
            }
        }catch (SQLException e){
            throw new DaoException("error",e);
        }
        throw new DaoException("error");
    }

    @Override
    public boolean remove(Long id) throws DaoException {
    try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE)){
        preparedStatement.setLong(1,id);
       return preparedStatement.executeUpdate()>0;
    }catch (SQLException e){
        throw new DaoException("error",e);
        }
    }

    @Override
    public boolean update(Long id, String course, Long toId, Long fromId) throws DaoException {
       try(Connection connection= connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
           preparedStatement.setInt(1, Integer.parseInt(course));
           preparedStatement.setLong(2,toId);
           preparedStatement.setLong(3,fromId);
           preparedStatement.setLong(4,id);
           return preparedStatement.executeUpdate()>0;
       }catch (SQLException e){
           throw new DaoException("error",e);
       }
    }

    @Override
    public List<Data> getAll() throws DaoException {
        List<Data> data = new ArrayList<>();
        try(Connection connection= connectionPool.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                data.add(Data.builder().id(resultSet.getLong(1)).
                        course(resultSet.getInt(2)).
                        to(Course.builder().id(resultSet.getLong(3)).name(resultSet.getString(4)).build()).
                        from(Course.builder().id(resultSet.getLong(5)).name(resultSet.getString(6)).build()).build());
            }
        }catch (SQLException e){
            throw new DaoException("error",e);
        }
        return data;
    }
}
