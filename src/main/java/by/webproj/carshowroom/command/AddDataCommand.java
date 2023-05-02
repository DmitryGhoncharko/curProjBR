package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.DataDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddDataCommand implements Command{
    private final RequestFactory requestFactory;
    private final DataDao dao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String cr = request.getParameter("cr");
        String to = request.getParameter("crName");
        String from = request.getParameter("crName1");
        dao.add(cr,Long.valueOf(to),Long.valueOf(from));
        return requestFactory.createRedirectResponse("/controller?command=ss");
    }
}
