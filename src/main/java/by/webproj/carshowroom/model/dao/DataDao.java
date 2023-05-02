package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Data;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;

public interface DataDao {
    Data add(String course, Long toId, Long fromId) throws DaoException;

    boolean remove(Long id) throws DaoException;

    boolean update(Long id, String course, Long toId, Long fromId) throws DaoException;

    List<Data> getAll() throws DaoException;
}
