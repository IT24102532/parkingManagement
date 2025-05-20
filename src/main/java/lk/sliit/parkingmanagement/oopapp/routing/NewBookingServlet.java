// This servlet handles requests to create a new parking slot booking
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDao;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDao;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "NewBookingServlet", value = "/book/new")
public class NewBookingServlet extends HttpServlet {

    // Logger to help track errors or useful information
    private final Logger LOGGER = Logger.getLogger(NewBookingServlet.class.getName());

    // DAOs to interact with parking slot and booking data
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();

    // This method handles POST requests when a user submits the new booking form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Try to get the current session (without creating a new one if it doesn't exist)
        HttpSession session = request.getSession(false);

        // Get the currently logged-in user's ID from the session
        String userId = (String) session.getAttribute("user");

        // Collect the booking form data from the request
        String slotId = request.getParameter("slotId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String vehicleId = request.getParameter("vehicleId");
        
        try {
            // Pass all the booking details to the JSP so it can show them on the confirmation page
            request.setAttribute("user", userId);
            request.setAttribute("slotId", slotId);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("vehicle", vehicleId);

            // Forward the request to the booking.jsp page for review/confirmation
            request.getRequestDispatcher("/views/booking.jsp").forward(request, response);

        } catch (Exception e) {
            // If anything fails, log the issue and return a server error response
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

}