// This servlet handles everything related to parking search actions —
// like loading the search page, processing search input, and forwarding to results
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SearchRoutingServlet", value = "/search")
public class SearchRoutingServlet extends HttpServlet {

    // Logger to record errors or other important information during request processing
    private final Logger LOGGER = Logger.getLogger(SearchRoutingServlet.class.getName());

    // This method handles GET requests, usually when someone first visits the search page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session (don't create a new one if none exists)
        HttpSession session = request.getSession(false);

        // Retrieve the currently logged-in user's ID from the session
        String userId = (String) session.getAttribute("user");

        try {
            // Add the user ID to the request so the JSP page can access it
            request.setAttribute("user", userId);

            // Forward the user to the long-term search page
            request.getRequestDispatcher("/views/long_term_search.jsp").forward(request, response);

        } catch (Exception e) {
            // Log any error that occurs and return a 500 error response
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    // This method handles POST requests — like when the user submits the search form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("user");

        // Extract the type of search and the action the user wants to perform
        String type = request.getParameter("type"); // e.g., "long_term" or "insta"
        String action = request.getParameter("action"); // e.g., "continue", "insta", or "longTerm"

        // If the user clicked "Continue" after filling the form
        if ("continue".equalsIgnoreCase(action)) {
            try {
                // Set attributes to pass to the search results page
                request.setAttribute("user", userId);
                request.setAttribute("startDate", request.getParameter("startDate"));
                request.setAttribute("endDate", request.getParameter("endDate"));
                request.setAttribute("vehicle", request.getParameter("vehicle"));
                request.setAttribute("location", request.getParameter("location"));

                // Forward to the appropriate results page based on the type of search
                if ("long_term".equals(type)) {
                    request.getRequestDispatcher("/views/lt_search_results.jsp").forward(request, response);
                } else if ("insta".equals(type)) {
                    request.getRequestDispatcher("/views/insta_results.jsp").forward(request, response);
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
            }
        }

        // If the user selected the instant booking option from the UI
        else if ("insta".equalsIgnoreCase(action)) {
            request.setAttribute("user", userId);
            request.getRequestDispatcher("/views/insta.jsp").forward(request, response);
        }

        // If the user chose the long-term parking option
        else if ("longTerm".equalsIgnoreCase(action)) {
            request.setAttribute("user", userId);
            request.getRequestDispatcher("/views/lt_term_search.jsp").forward(request, response);
        }
    }
}