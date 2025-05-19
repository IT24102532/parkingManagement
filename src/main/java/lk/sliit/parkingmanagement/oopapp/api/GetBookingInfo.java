package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
*Servlet designed to retrieve detailed information for a specific booking
*
* @param id
*
*Only listens to get requests and returns an errors for any other method
*This servlet reads data from booking,transaction,parkingSlot and user tables
*It retrieves the booking and finds the associated transaction and user
*
*The all collected data is merged into a single map and returned as a structured JSON object
*/

@WebServlet(name = "GetBookingInfo", value = "/get/booking/info")
public class GetBookingInfo extends HttpServlet {
    //DAO access
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    //Gson Builder with custom adapters
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //log the request for tracking purposes
        Log.type(LogType.INFO).message("/get/booking/info").print();

        String bookingId = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //Declare objects to hold booking data
        Booking booking = null;
        User user = null;
        Transaction transaction = null;
        ParkingSlot parkingSlot = null;
        try {
            //Retrieve booking details by bookingID
            booking = bookingDao.getById(bookingId);
            //Find the transaction
            transaction = transactionDao.findAll().stream()
                    .filter(t -> t.getBookingId().equalsIgnoreCase(bookingId))
                    .collect(Collectors.toList())
                    .get(0);
            List<Map<String, Object>> joinedData = new ArrayList<>();
            //Getting user and parking slot details
            user = userDao.getById(transaction.getUserId());
            parkingSlot = parkingSlotDao.getById(booking.getSlotId());

            // Data response
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("transactionId", transaction.getTransactionId());
            entry.put("amount", transaction.getAmount());
            entry.put("location", parkingSlot.getLocationName());
            entry.put("slotName", parkingSlot.getSlotName());
            entry.put("bookingId", booking.getBookingId());
            entry.put("slotId", booking.getSlotId());
            entry.put("startDateTime", booking.getStartDateTime());
            entry.put("endTime", booking.getTimeOut());
            System.out.println(entry);
            joinedData.add(entry);
            response.getWriter().write(gson.toJson(joinedData));
            Log.type(LogType.SUCCESS).message("[get/booking/info] Successfully delivered data").print();
        }
        catch (Exception e) {
            //handle and log any errors 
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch bookings.");
            Log.type(LogType.ERROR).message("[get/booking/info] Failed to deliver bookings.").print();
        }
    }
}