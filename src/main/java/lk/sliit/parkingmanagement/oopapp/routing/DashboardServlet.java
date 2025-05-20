// This servlet handles GET requests to the "/dashboard" URL
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {

    // Logger to track errors or useful information
    private final Logger LOGGER = Logger.getLogger(DashboardServlet.class.getName());

    // This method runs when someone sends a GET request to "/dashboard"
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Try to get the current session (don’t create a new one if it doesn't exist)
        HttpSession session = request.getSession(false);

        // If there's no session or no user is logged in, redirect to the login page
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // If the user is logged in, get their user ID from the session
        String userId = (String) session.getAttribute("user");
        try {
            // Pass the user ID to the JSP page so it can display relevant info
            request.setAttribute("userId", userId);

            // Forward the request to the dashboard JSP page
            request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            // If something goes wrong, log the error and return a server error response
            LOGGER.log(Level.SEVERE, "Error loading dashboard", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}