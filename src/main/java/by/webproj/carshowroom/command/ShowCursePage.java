package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Course;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.CourseDao;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShowCursePage implements Command{
    private final RequestFactory requestFactory;
    private final CourseDao courseDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        List<Course> courses=  courseDao.getAll();
        request.addAttributeToJsp("course",courses);
        return requestFactory.createForwardResponse(PagePath.COURSE_PAGE.getPath());
    }
}
