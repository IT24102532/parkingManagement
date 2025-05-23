package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.*;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    This servlet handles the creation of parking slot bookings for users
    It has two types of bookings:
           - InstaSlot booking
                + Used for short term bookings or hourly bookings

           - LongTermSlot booking
                + Used for long term bookings or daily bookings

     Only listen for POST requests
 */

@WebServlet(name = "BookingServlet", value = "/book/create")
public class BookingServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BookingServlet.class.getName());

    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();

    //disallow the GET request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        Log.type(LogType.INFO).message("Get request not allowed");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get current session, return error if session is invalid or missing data
        HttpSession session = request.getSession(false);
        if (!validateSessionData(session, response)) return;

        // Extract session attributes
        String userId = (String) session.getAttribute("user");
        String slotId = (String) session.getAttribute("slotId");
        String startDateStr = (String) session.getAttribute("startDate");
        String endDateStr = (String) session.getAttribute("endDate");

        ParkingSlot slot;
        try {
            // Retrieve slot details from DB
            slot = parkingSlotDao.getById(slotId);
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, "Slot not found.", e);
            return;
        }

        try {
            // Decide booking type and process accordingly
            if (slot instanceof InstaSlot) {
                handleInstaSlotBooking((InstaSlot) slot, userId, slotId, startDateStr, endDateStr, session, response, request);
            } else if (slot instanceof LongTermSlot) {
                handleLongTermSlotBooking((LongTermSlot) slot, userId, slotId, startDateStr, endDateStr, session, response, request);
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Unsupported slot type.");
            }
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error occurred.", e);
        }
    }

    // Check if session exists and contains all necessary attributes
    private boolean validateSessionData(HttpSession session, HttpServletResponse response) throws IOException {
        if (session == null || session.getAttribute("user") == null ||
                session.getAttribute("slotId") == null ||
                session.getAttribute("startDate") == null ||
                session.getAttribute("endDate") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing session data or user not authenticated.");
            return false;
        }
        return true;
    }

    private void handleInstaSlotBooking(InstaSlot slot, String userId, String slotId, String startStr, String endStr, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        LocalDateTime startTime, endTime;

        try {
            // Parse input strings to LocalDateTime
            startTime = LocalDateTime.parse(startStr);
            endTime = LocalDateTime.parse(endStr);
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid datetime format.", e);
            session.setAttribute("bookingStatus", false);
            return;
        }

        // Validate time range
        if (!endTime.isAfter(startTime)) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "End time must be after start time.");
            session.setAttribute("bookingStatus", false);
            return;
        }

        // Check slot availability
        if (!isSlotStillAvailable(parkingSlotDao.getAvailableSlotsByHours(startTime, endTime, slot.getLocation()), slotId)) {
            sendError(response, HttpServletResponse.SC_CONFLICT, "Slot is already booked for the selected time.");
            session.setAttribute("bookingStatus", false);
            return;
        }

        // Calculate duration and total cost
        long duration = Duration.between(startTime, endTime).toHours();
        double totalAmount = slot.getPrice() * duration;

        //Create booking and transaction
        Booking booking = new Booking(slotId, false, false, startTime);
        booking.setTimeOut(endTime);
        Transaction transaction = new Transaction(booking.getBookingId(), userId, totalAmount, 0, true);

        try {
            bookingDao.create(booking);
            transactionDao.create(transaction);
            parkingSlotDao.updateHours(slotId, startTime, endTime);
            LOGGER.info("Booking " + booking.getBookingId() + " and transaction " + transaction.getTransactionId() + " successfully created");
            session.setAttribute("bookingStatus", true);
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/book/status"));
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Booking failed.", e);
            session.setAttribute("bookingStatus", false);
        }
    }

    private void handleLongTermSlotBooking(LongTermSlot slot, String userId, String slotId, String startStr, String endStr, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        LocalDate startDate, endDate;
        try {
            // Parse string to LocalDate
            startDate = LocalDate.parse(startStr);
            endDate = LocalDate.parse(endStr);
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.", e);
            session.setAttribute("bookingStatus", false);
            return;
        }

        //Convert to LocalDateTime for consistency
        LocalDateTime startTime = startDate.atStartOfDay();
        LocalDateTime endTime = endDate.atStartOfDay();

        if (!endTime.isAfter(startTime)) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "End date must be after start date.");
            session.setAttribute("bookingStatus", false);
            return;
        }

        //Check slot availability for date range
        if (!isSlotStillAvailable(parkingSlotDao.getAvailableSlotsByDates(startDate, endDate, slot.getLocation()), slotId)) {
            sendError(response, HttpServletResponse.SC_CONFLICT, "Slot is already booked for the selected dates.");
            session.setAttribute("bookingStatus", false);
            return;
        }

        long duration = Duration.between(startTime, endTime).toDays();
        double totalAmount = slot.getPrice() * duration;

        Booking booking = new Booking(slotId, false, false, startTime);
        booking.setTimeOut(endTime);
        Transaction transaction = new Transaction(booking.getBookingId(), userId, totalAmount, 0, true);

        try {
            bookingDao.create(booking);
            transactionDao.create(transaction);
            parkingSlotDao.updateDates(slotId, startDate, endDate);
            LOGGER.info("Booking " + booking.getBookingId() + " and transaction " + transaction.getTransactionId() + " successfully created");
            session.setAttribute("bookingStatus", true);
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/book/status"));
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Booking failed.", e);
            session.setAttribute("bookingStatus", false);
        }
    }

    //check if the target slot ID exists in the list of available slots
    private boolean isSlotStillAvailable(java.util.List<ParkingSlot> availableSlots, String targetSlotId) {
        return availableSlots.stream().anyMatch(slot -> slot.getSlotId().equals(targetSlotId));
    }

    //send a simple error response with logging
    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        LOGGER.warning(message);
        response.sendError(statusCode, message);
    }

    //Send an error response and logs the exception details
    private void sendError(HttpServletResponse response, int statusCode, String message, Exception e) throws IOException {
        Log.type(LogType.ERROR).message(e.getMessage()).print();
        response.sendError(statusCode, message);
    }
}

