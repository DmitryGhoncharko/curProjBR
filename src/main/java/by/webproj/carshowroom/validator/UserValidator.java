package by.webproj.carshowroom.validator;

import by.webproj.carshowroom.entity.User;

public interface UserValidator {
    /**
     *
     * @param login
     * @param password
     * @param secretKey
     * @return true if valid
     */
    boolean validateUserDataByLoginAndPasswordWithSecretKey(String login, String password, String secretKey);

    /**
     *
     * @param login
     * @param password
     * @return true if valid
     */
    boolean validateUserDataByLoginAndPassword(String login, String password);
}
