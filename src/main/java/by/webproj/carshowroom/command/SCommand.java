package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Course;
import by.webproj.carshowroom.entity.Data;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.CourseDao;
import by.webproj.carshowroom.model.dao.DataDao;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class SCommand implements Command{
    private final RequestFactory requestFactory;
    private final DataDao dao;
    private final CourseDao courseDao;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        List<Data> data = dao.getAll();
        List<Course> courses= courseDao.getAll();
        if(request.getParameter("sort")!=null){
            data.sort(Comparator.comparing(Data::getCourse));
        }
        request.addAttributeToJsp("data",data);
        request.addAttributeToJsp("cr",courses);
        return requestFactory.createForwardResponse(PagePath.S_PAGE.getPath());
    }
}
