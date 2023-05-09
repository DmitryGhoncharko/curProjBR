package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Course;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    /**
     *
     * @param name
     * @return course with id if added
     * @throws DaoException
     */
    Course add(String name) throws DaoException;

    /**
     *
     * @param id
     * @return course by id
     * @throws DaoException
     */
    Optional<Course> get(Long id) throws DaoException;

    /**
     *
     * @param id
     * @return true if remove
     * @throws DaoException
     */
    boolean remove(Long id) throws DaoException;

    /**
     *
     * @param id
     * @param name
     * @return true if update
     * @throws DaoException
     */
    boolean update(Long id, String name) throws  DaoException;

    /**
     *
     * @return list courses
     * @throws DaoException
     */
    List<Course> getAll() throws DaoException;
}
