package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.CourseDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DelCommand implements Command{
    private final RequestFactory requestFactory;
    private final CourseDao courseDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String id = request.getParameter("id");
        courseDao.remove(Long.valueOf(id));
        return requestFactory.createRedirectResponse("/controller?command=curse");
    }
}
