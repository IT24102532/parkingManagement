// This servlet takes care of showing the Instagram-style page in the app
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "InstaRoutingServlet", value = "/insta")
public class InstaRoutingServlet extends HttpServlet {

    // Used for logging any issues or important information
    private final Logger LOGGER = Logger.getLogger(InstaRoutingServlet.class.getName());

    // This method runs when someone visits the /insta page using a GET request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Try to get the current session, but don’t create a new one if it doesn’t exist
        HttpSession session = request.getSession(false);

        // Get the user ID from the session (assuming the user is already logged in)
        String userId = (String) session.getAttribute("user");
        try {
            // Pass the user ID to the JSP page so it can be used there
            request.setAttribute("user", userId);

            // Show the Insta page (probably a custom social feed or something similar)
            request.getRequestDispatcher("/views/insta.jsp").forward(request, response);
        } catch (Exception e) {
            // If anything goes wrong, log the error and return a generic server error message
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}