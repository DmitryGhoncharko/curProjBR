package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.controller.SimpleRequestFactory;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import by.webproj.carshowroom.model.connection.HikariCPConnectionPool;
import by.webproj.carshowroom.model.dao.*;
import by.webproj.carshowroom.model.service.*;
import by.webproj.carshowroom.securiy.BcryptWithSaltHasherImpl;
import by.webproj.carshowroom.securiy.PasswordHasher;
import by.webproj.carshowroom.validator.SimpleUserValidator;
import by.webproj.carshowroom.validator.UserValidator;

public class InitialContext {
    private final ConnectionPool hikariCPConnectionPool = new HikariCPConnectionPool();
    private final UserDao simplePageDao = new SimpleUserDao(hikariCPConnectionPool);
    private final UserValidator simplePageServiceValidator = new SimpleUserValidator();
    private final PasswordHasher bcryptWithSaltHasher = new BcryptWithSaltHasherImpl();
    private final UserService simpleUserService = new SimpleUserService(simplePageServiceValidator, simplePageDao, bcryptWithSaltHasher);
    private final RequestFactory simpleRequestFactory = new SimpleRequestFactory();
    private final BalanceDao balanceDao = new SimpleBalanceDao(hikariCPConnectionPool);
    private final CourseDao courseDao = new SimpleCourseDao(hikariCPConnectionPool);
    private final DataDao dao = new SimpleDataDao(hikariCPConnectionPool);
    public Command lookup(String commandName) {

        switch (commandName) {
            case "logincmnd":
                return new LoginCommand(simpleUserService, simpleRequestFactory);
            case "login":
                return new ShowLoginPageCommand(simpleRequestFactory);
            case "logout":
                return new LogoutCommand(simpleRequestFactory);
            case "registration":
                return new ShowRegistrationPageCommand(simpleRequestFactory);
            case "registrationcmnd":
                return new RegistrationCommand(simpleUserService, simpleRequestFactory);
            case "cab":
                return new ShowCabinetPageCommand(simpleRequestFactory,balanceDao,courseDao);
            case "upBal":
                return new UpdateBalanceCommand(simpleRequestFactory,balanceDao);
            case "addBal":
                return new AddBalanceCommand(simpleRequestFactory,balanceDao);
            case "update":
                return new UpdateCommand(simpleRequestFactory,courseDao);
            case "add":
                return new AddCommand(simpleRequestFactory,courseDao);
            case "val":
                return new ShowCursePage(simpleRequestFactory,courseDao);
            case "ss":
                return new SCommand(simpleRequestFactory,dao,courseDao);
            case "curse":
                return new ShowCursePage(simpleRequestFactory,courseDao);
            case "addData":
                return new AddDataCommand(simpleRequestFactory,dao);
            case "del":
                return new DelCommand(simpleRequestFactory,courseDao);
            case "delete":
                return new DeleteCommand(simpleRequestFactory,dao);
            case "stat":
                return new ShowStatPageCommand(simpleRequestFactory,simplePageDao);
                default:
                return new ShowMainPageCommand(simpleRequestFactory);
        }
    }
}
