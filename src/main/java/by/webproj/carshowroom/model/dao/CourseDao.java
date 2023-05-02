package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Course;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    Course add(String name) throws DaoException;

    Optional<Course> get(Long id) throws DaoException;
    boolean remove(Long id) throws DaoException;

    boolean update(Long id, String name) throws  DaoException;

    List<Course> getAll() throws DaoException;
}
