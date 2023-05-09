package by.webproj.carshowroom.command;

public interface CommandResponse {
    /**
     *
     * @return true if redirect
     */
    boolean isRedirect();

    /**
     *
     * @return path
     */
    String getPath();

}