package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "NewBookingServlet", value = "/book/new")
public class NewBookingServlet extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(NewBookingServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("user");
        try {
            request.setAttribute("user", userId);
            request.getRequestDispatcher("/views/booking.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}