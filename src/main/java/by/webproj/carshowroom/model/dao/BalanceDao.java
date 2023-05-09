package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Balance;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BalanceDao {
    /***
     *
     * @param userId
     * @param count
     * @param courseId
     * @return balance with id if added
     * @throws DaoException
     */
    Balance add(Long userId, Double count, Long courseId) throws DaoException;

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
     * @param userId
     * @param count
     * @param courseId
     * @return true if update
     * @throws DaoException
     */
    boolean update(Long id,Long userId, Double count, Long courseId ) throws DaoException;

    /**
     *
     * @return all balances
     * @throws DaoException
     */
    List<Balance> getAll() throws DaoException;

    /**
     *
     * @param userId
     * @return balances by user id
     * @throws DaoException
     */
    List<Balance> getByUserId(Long userId) throws DaoException;

    /**
     *
     * @param id
     * @return balance by id
     * @throws DaoException
     */
    Optional<Balance> get(Long id) throws DaoException;
}
