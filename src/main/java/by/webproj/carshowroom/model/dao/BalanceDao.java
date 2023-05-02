package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Balance;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BalanceDao {
    Balance add(Long userId, Double count, Long courseId) throws DaoException;

    boolean remove(Long id) throws DaoException;

    boolean update(Long id,Long userId, Double count, Long courseId ) throws DaoException;

    List<Balance> getAll() throws DaoException;

    List<Balance> getByUserId(Long userId) throws DaoException;

    Optional<Balance> get(Long id) throws DaoException;
}
