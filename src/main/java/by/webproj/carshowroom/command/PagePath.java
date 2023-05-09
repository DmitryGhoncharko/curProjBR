package by.webproj.carshowroom.command;

public enum PagePath {
    MAIN_PAGE("/WEB-INF/jsp/main.jsp"), LOGIN_PAGE("/WEB-INF/jsp/login.jsp"), INDEX_PATH("/"),
    REGISTRATION_PAGE("/WEB-INF/jsp/registration.jsp"),
    CAB_PAGE("/WEB-INF/jsp/cab.jsp"), COURSE_PAGE("/WEB-INF/jsp/course.jsp"), S_PAGE("/WEB-INF/jsp/s.jsp"),
    STAT_PAGE("/WEB-INF/jsp/stat.jsp");
    private final String path;

    PagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
