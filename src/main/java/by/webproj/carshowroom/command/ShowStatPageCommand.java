package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.UserDao;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShowStatPageCommand implements Command{
    private final RequestFactory requestFactory;
    private final UserDao userDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        List<User> userList = userDao.findAll();
        if(request.getParameter("client")!=null){
            userList = userDao.findAllClients();
        }
        request.addAttributeToJsp("count",userList.size());
        request.addAttributeToJsp("data",userList);
        return  requestFactory.createForwardResponse(PagePath.STAT_PAGE.getPath());
    }
}
