package by.webproj.carshowroom.command;


import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;


public interface Command {
    /**
     *
      * @param request
     * @return command response object
     * @throws ServiceError
     * @throws DaoException
     */
    CommandResponse execute(CommandRequest request) throws ServiceError, DaoException;

}
