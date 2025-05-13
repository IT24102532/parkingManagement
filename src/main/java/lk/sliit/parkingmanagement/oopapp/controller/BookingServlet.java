package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.*;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "BookingServlet", value = "/book/create")
public class BookingServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BookingServlet.class.getName());

    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null ||
                session.getAttribute("slotId") == null ||
                session.getAttribute("startDate") == null ||
                session.getAttribute("endDate") == null) {
            System.out.println("reached session validated and died");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing session data or user not authenticated.");
            return;
        }

        String userId = (String) session.getAttribute("user");
        String slotId = (String) session.getAttribute("slotId");
        String startDateStr = (String) session.getAttribute("startDate");
        String endDateStr = (String) session.getAttribute("endDate");
        System.out.println("reached session validated");
        System.out.println(userId);
        System.out.println(slotId);
        System.out.println(startDateStr);
        System.out.println(endDateStr);

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        LocalDateTime startTime, endTime;
        try {
            startTime = startDate.atStartOfDay();
            endTime = endDate.atStartOfDay();
            System.out.println("tried to parse start date and end date");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Parsing start date and end date failed", e);
            session.setAttribute("bookingStatus", false);
            return;
        }
        System.out.println("dates worked moving on");
        ParkingSlot slot;
        try {
            slot = parkingSlotDao.getById(slotId);
            if (!(slot instanceof LongTermSlot)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only long-term slots can be booked here.");
                return;
            }
            System.out.println("tried to assing longterm slot and succeeded");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch parking slot", e);
            session.setAttribute("bookingStatus", false);
            return;
        }

        LongTermSlot longTermSlot = (LongTermSlot) slot;

        try {
            boolean isAvailable = parkingSlotDao.getAvailableSlotsByDates(startDate, endDate, longTermSlot.getLocation())
                    .stream()
                    .anyMatch(s -> s.getSlotId().equals(slotId));

            if (!isAvailable) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Slot is already booked for the selected dates.");
                return;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking slot availability", e);
            session.setAttribute("bookingStatus", false);
            return;
        }


        long durationInHours = Duration.between(startTime, endTime).toDays();
        if (durationInHours <= 0) {
            session.setAttribute("bookingStatus", false);
            return;
        }
        System.out.println(durationInHours);
        double pricePerHour = longTermSlot.getPrice();
        double totalAmount = pricePerHour * durationInHours;
        System.out.println(totalAmount);

        Booking booking = new Booking(slotId, false, false, startTime);
        booking.setTimeOut(endTime);
        Transaction transaction = new Transaction(booking.getBookingId(), userId, totalAmount, 0, true);

        try {
            bookingDao.create(booking);
            transactionDao.create(transaction);
            parkingSlotDao.updateDates(slotId, startTime.toLocalDate(), endTime.toLocalDate());
            session.setAttribute("bookingStatus", true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while creating booking or transaction", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to complete booking.");
            session.setAttribute("bookingStatus", false);
            return;
        }

        LOGGER.info("Booking " + booking.getBookingId() + " successfully created");
        LOGGER.info("Transaction " + transaction.getTransactionId() + " successfully created");
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/book/status"));
    }
}
