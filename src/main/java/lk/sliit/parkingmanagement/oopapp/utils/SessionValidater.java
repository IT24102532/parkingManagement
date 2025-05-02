package lk.sliit.parkingmanagement.oopapp.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface SessionValidater {
    boolean validateSession(HttpServletRequest session);
    void handleInvalidate(HttpServletRequest session, HttpServletResponse response) throws IOException, ServletException;
}
