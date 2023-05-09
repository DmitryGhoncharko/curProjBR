package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Data;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;

public interface DataDao {
    /**
     *
     * @param course
     * @param toId
     * @param fromId
     * @return data with id if added
     * @throws DaoException
     */
    Data add(String course, Long toId, Long fromId) throws DaoException;

    /**
     *
     * @param id
     * @return true if removed
     * @throws DaoException
     */
    boolean remove(Long id) throws DaoException;

    /**
     *
     * @param id
     * @param course
     * @param toId
     * @param fromId
     * @return true if update
     * @throws DaoException
     */
    boolean update(Long id, String course, Long toId, Long fromId) throws DaoException;

    /**
     *
     * @return list data
     * @throws DaoException
     */
    List<Data> getAll() throws DaoException;
}
