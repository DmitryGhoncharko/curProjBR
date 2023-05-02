package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Balance;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.BalanceDao;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UpdateBalanceCommand implements Command{
    private final RequestFactory requestFactory;
    private final BalanceDao balanceDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String count = request.getParameter("count");
        String id = request.getParameter("id");
        Optional<Balance> balance = balanceDao.get(Long.valueOf(id));
        Balance real = balance.get();
        balanceDao.update(Long.valueOf(id),real.getUserId(), Double.valueOf(count), real.getCourseId());
        return requestFactory.createRedirectResponse("/controller?command=cab");
    }
}
