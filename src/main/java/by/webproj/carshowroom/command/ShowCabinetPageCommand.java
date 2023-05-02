package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.dto.BalanceDTO;
import by.webproj.carshowroom.entity.Balance;
import by.webproj.carshowroom.entity.Course;
import by.webproj.carshowroom.entity.Data;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.BalanceDao;
import by.webproj.carshowroom.model.dao.CourseDao;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ShowCabinetPageCommand implements Command{
    private final RequestFactory requestFactory;
    private final BalanceDao balanceDao;
    private final CourseDao courseDao;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        Optional<Object> user = request.retrieveFromSession("user");
        User realUser = (User) user.get();
        List<Balance> balances = balanceDao.getByUserId(realUser.getId());
        List<BalanceDTO> balanceDTOS = new ArrayList<>();
        for (Balance balance : balances){
            Optional<Course> course = courseDao.get(balance.getCourseId());
            if(course.isPresent()){
                balanceDTOS.add(BalanceDTO.builder().id(balance.getId()).
                        userId(balance.getUserId()).
                         count(balance.getBalance()).
                        name(course.get().getName()).build());
            }
        }
        List<Course> courses = courseDao.getAll();
        request.addAttributeToJsp("cr",courses);
        request.addAttributeToJsp("data",balanceDTOS);
        return requestFactory.createForwardResponse(PagePath.CAB_PAGE.getPath());
    }
}
