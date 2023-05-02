package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Balance;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.BalanceDao;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AddBalanceCommand implements Command{
    private final RequestFactory requestFactory;
    private final BalanceDao balanceDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String crId = request.getParameter("crName");
        String count = request.getParameter("countAdd");
        Optional<Object> user = request.retrieveFromSession("user");
        User realUser= (User) user.get();
        balanceDao.add(realUser.getId(),Double.valueOf(count), Long.valueOf(crId));
        return requestFactory.createRedirectResponse("/controller?command=cab");
    }
}
