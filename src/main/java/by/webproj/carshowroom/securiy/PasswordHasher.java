package by.webproj.carshowroom.securiy;

public interface PasswordHasher {
    /**
     *
     * @param password
     * @param passwordHash
     * @return true if passwords equals
     */
    boolean checkIsEqualsPasswordAndPasswordHash(String password, String passwordHash);

    /**
     *
     * @param password
     * @return password hash
     */
    String hashPassword(String password);
}
