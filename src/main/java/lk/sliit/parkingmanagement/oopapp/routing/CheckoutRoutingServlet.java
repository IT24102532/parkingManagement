// This servlet handles requests to the "/checkout" URL
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CheckoutRoutingServlet", value = "/checkout")
public class CheckoutRoutingServlet extends HttpServlet {
    // Set up a logger to record any issues or important info
    private final Logger LOGGER = Logger.getLogger(CheckoutRoutingServlet.class.getName());

    // This method is called when a POST request is made to "/checkout"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Try to get the current session, but don’t create a new one if it doesn’t exist
        HttpSession session = request.getSession(false);

        // Grab the user ID from the session
        String userId = (String) session.getAttribute("user");

        // Store some new values in the session that we got from the form/request
        session.setAttribute("slotId", request.getParameter("slotId"));
        session.setAttribute("startDate", request.getParameter("startDate"));
        session.setAttribute("endDate", request.getParameter("endDate"));

        // Print out session values for debugging (not ideal in production!)
        System.out.println(session.getAttribute("slotId"));
        System.out.println(session.getAttribute("startDate"));
        System.out.println(session.getAttribute("endDate"));
        System.out.println(session.getAttribute("user"));
        try {
            // Pass the user info to the JSP so it can display it
            request.setAttribute("user", userId);

            // Forward the request to the checkout page (JSP)
            request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
        } catch (Exception e) {
            // If anything goes wrong, log it and send a server error back to the user
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}