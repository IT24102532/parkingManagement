package lk.sliit.parkingmanagement.oopapp.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultUserValidater implements SessionValidater {
    private final Logger LOGGER = Logger.getLogger(DefaultUserValidater.class.getName());
    @Override
    public boolean validateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Object user = session.getAttribute("user");
            Object timeout = session.getAttribute("timeout");

            if (user != null && timeout != null) {
                try {
                    LocalDateTime currentTimeout = LocalDateTime.parse(timeout.toString());
                    LocalDateTime now = LocalDateTime.now();
                    return currentTimeout.isBefore(now);
                }
                catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error parsing session timeout", e);
                }
            }
        }
        return false;
    }

    @Override
    public void handleInvalidate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }
}

