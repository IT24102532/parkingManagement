package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDao;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dao.TransactionDao;
import lk.sliit.parkingmanagement.oopapp.dao.TransactionDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import com.google.gson.Gson;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
/*
*Servlet designed to retrieve all bookings along with a specific user
*
* param user - to filter transactions and bookings
* Reads data from transactions and booking table
*
* for each transaction booking pair,transaction id,amount,booking id
* slot id and start time are merged into a structured map
*
*/

@WebServlet(name = "UserBookingsServlet", value = "/get/bookings/user")
public class UserBookingsServlet extends HttpServlet {
    //DAO Access
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();
    //Handles get requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            //Retrieve all transactions belongs to the given user
            List<Transaction> userTransactions = transactionDao.findAll().stream()
                    .filter(t -> t.getUserId().equals(userId))
                    .collect(Collectors.toList());
            //extract unique booking id s
            Set<String> bookingIds = userTransactions.stream()
                    .map(Transaction::getBookingId)
                    .collect(Collectors.toSet());
            //get all bookings that match the extracted booking ID s
            List<Booking> userBookings = bookingDao.findAll().stream()
                    .filter(b -> bookingIds.contains(b.getBookingId()))
                    .collect(Collectors.toList());
            List<Map<String, Object>> joinedData = new ArrayList<>();
            //for each transaction,find the matching booking and map the details
            for (Transaction transaction : userTransactions) {
                userBookings.stream()
                        .filter(booking -> booking.getBookingId().equals(transaction.getBookingId()))
                        .findFirst()
                        .ifPresent(booking -> {
                            Map<String, Object> entry = new LinkedHashMap<>();
                            entry.put("transactionId", transaction.getTransactionId());
                            entry.put("amount", transaction.getAmount());
                            entry.put("bookingId", booking.getBookingId());
                            entry.put("slotId", booking.getSlotId());
                            entry.put("startDateTime", booking.getStartDateTime());
                            System.out.println(entry);
                            joinedData.add(entry);
                        });
            }
            // Convert the list of user bookings into JSON
            response.getWriter().write(gson.toJson(userBookings));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch bookings.");
        }
    }
}