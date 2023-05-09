package by.webproj.carshowroom.controller;

import by.webproj.carshowroom.command.CommandRequest;
import by.webproj.carshowroom.command.CommandResponse;


import javax.servlet.http.HttpServletRequest;

public interface RequestFactory {
    /**
     * ]
     * @param request
     * @return command request after create
     */
    CommandRequest createRequest(HttpServletRequest request);

    /**
     *
     * @param path
     * @return command response after create and forward
     */
    CommandResponse createForwardResponse(String path);

    /**
     *
     * @param path
     * @return command response after create and redirect
     */
    CommandResponse createRedirectResponse(String path);

}
