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
    private final Logger LOGGER = Logger.getLogger(NewBookingServlet.class.getName());
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("user");
        String slotId = request.getParameter("slotId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String vehicleId = request.getParameter("vehicleId");
        
        try {
            request.setAttribute("user", userId);
            request.setAttribute("slotId", slotId);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("vehicle", vehicleId);
            request.getRequestDispatcher("/views/booking.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

}