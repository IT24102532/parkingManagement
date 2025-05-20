// This package contains classes responsible for handling web request routing
// and controlling the flow of the parking management application.
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UserLogsServlet", value = "/logs")
public class UserLogsServlet extends HttpServlet {
    // Logger to keep track of any errors or important info during servlet execution
    private final Logger LOGGER = Logger.getLogger(UserLogsServlet.class.getName());

    // Handles GET requests made to /logs URL
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the existing session if there is one; do not create a new session
        HttpSession session = request.getSession(false);

        // Retrieve the user ID stored in the session (assumed to be saved under "user")
        String userId = (String) session.getAttribute("user");

        try {
            // Add the user ID as a request attribute so the JSP can access it
            request.setAttribute("user", userId);

            // Forward the request to the JSP page that shows user transaction logs
            request.getRequestDispatcher("/views/user-transactions.jsp").forward(request, response);
        } catch (Exception e) {
            // If anything goes wrong, log the error for debugging and send a 500 error to the client
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}