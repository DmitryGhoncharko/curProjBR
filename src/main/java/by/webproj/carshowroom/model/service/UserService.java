package by.webproj.carshowroom.model.service;

import by.webproj.carshowroom.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     *
     * @param login
     * @param password
     * @param secretKey
     * @return user if added as admin
     */
    User addUserAsAdmin(String login, String password, String secretKey);

    /**
     *
     * @param login
     * @param password
     * @return auth user id admin
     */
    Optional<User> authenticateIfAdmin(String login, String password);

    /**
     * auth user if client
     * @param login
     * @param password
     * @return
     */
    Optional<User> authenticateIfClient(String login, String password);

    /**
     *
     * @param login
     * @param password
     * @return return true if complete
     */
    boolean addUserAsClient(String login, String password);

    /**
     *
     * @return list users as clients
     */
    List<User> findAllClients();
}
