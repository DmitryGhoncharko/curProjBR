package by.webproj.carshowroom.command;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface CommandRequest {
    /**
     *
     * @param name attribute name
     * @param attribute attribute obj
     */
    void addAttributeToJsp(String name, Object attribute);

    /**
     *
     * @param name name
     * @return attribute obj
     */
    String getParameter(String name);

    /**
     *
     * @return true if session exist
     */
    boolean sessionExists();

    /**
     *
     * @param name
     * @param value
     * @return true if obj added to session
     */
    boolean addToSession(String name, Object value);

    /**
     *
     * @param name
     * @return object from session by name
     */
    Optional<Object> retrieveFromSession(String name);

    /**
     * clear session
     */
    void clearSession();

    /**
     * create session
     */
    void createSession();

    /**
     *  remove from session by name
     * @param name
     */
    void removeFromSession(String name);

    /**
     *
     * @return uri
     */
    String getURI();

    /**
     *
     * @param name
     * @return part data
     * @throws ServletException
     * @throws IOException
     */
    Part getPart(String name) throws ServletException, IOException;

    /**
     *
     * @return servlet context
     */
    ServletContext getServletContext();

    /**
     *
     * @return parts
     * @throws ServletException
     * @throws IOException
     */
    Collection<Part> getParts() throws ServletException, IOException;
}