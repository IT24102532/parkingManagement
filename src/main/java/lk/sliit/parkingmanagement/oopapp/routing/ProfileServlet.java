// This servlet handles loading the user's profile page
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    // Logger helps track errors and other useful info
    private final Logger LOGGER = Logger.getLogger(ProfileServlet.class.getName());

    // This method runs when a user visits the /profile page using a GET request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Try to get the current session, but don’t create a new one if it doesn’t exist
        HttpSession session = request.getSession(false);

        // Get the user ID that was saved in the session when they logged in
        String userId = (String) session.getAttribute("user");
        try {
            // Pass the user ID to the JSP page, so it can display profile-related info
            request.setAttribute("user", userId);

            // Forward the request to the profile page (profile.jsp)
            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);

        } catch (Exception e) {
            // If anything goes wrong, log the error and send a generic error response
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
