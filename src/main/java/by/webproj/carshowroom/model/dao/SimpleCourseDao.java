package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Course;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class SimpleCourseDao implements CourseDao{
    private static final String SQL_ADD_COURSE = "insert into course(course_name) values(?)";

    private static final String SQL_GET_ALL= "select course_id, course_name from course";

    private static final String SQL_GET_BY_ID = "select course_id, course_name from course where course_id = ?";

    private static final String SQL_DELETE_BY_ID = "delete from course where course_id = ?";

    private static final String SQL_UPDATE = "update course set course_name = ? where course_id = ?";
    private final ConnectionPool connectionPool;
    @Override
    public Course add(String name) throws DaoException {
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COURSE,Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,name);
            if(preparedStatement.executeUpdate()>0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
               if(resultSet.next()){
                   return  Course.builder().id(resultSet.getLong(1)).name(name).build();
               }
            }

        }catch (SQLException e){
            throw new DaoException("Error",e);
        }
        throw new DaoException("Error");
    }

    @Override
    public List<Course> getAll() throws DaoException {
        List<Course> courses = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);
            while (resultSet.next()){
                courses.add(Course.builder().id(resultSet.getLong(1)).name(resultSet.getString(2)).build());
            }
        }catch (SQLException e){
            throw new DaoException("error",e);
        }
        return courses;
    }

    @Override
    public Optional<Course> get(Long id) throws DaoException {
      try(Connection connection = connectionPool.getConnection();PreparedStatement preparedStatement= connection.prepareStatement(SQL_GET_BY_ID)){
          preparedStatement.setLong(1,id);

              ResultSet resultSet = preparedStatement.executeQuery();
             if(resultSet.next()){
                 return Optional.of(Course.builder().id(resultSet.getLong(1)).name(resultSet.getString(2)).build());
             }


      }catch (SQLException e){
          throw new DaoException("Error",e);
      }
       return Optional.empty();
    }

    @Override
    public boolean remove(Long id) throws DaoException {
       try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)){
           preparedStatement.setLong(1,id);
           return preparedStatement.executeUpdate()>0;
       }catch (SQLException e){
           throw new DaoException("Error",e);
       }
    }

    @Override
    public boolean update(Long id, String name) throws DaoException {
        try(Connection connection= connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)){
            preparedStatement.setString(1,name);
            preparedStatement.setLong(2,id);
            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            throw new DaoException("error",e);
        }
    }
}
