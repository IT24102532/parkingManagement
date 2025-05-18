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
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;
import lk.sliit.parkingmanagement.oopapp.utils.Sort.GlobalSort;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/*
* Servlet designed to retrieve all bookings as a JSON response
*
* @param userId
*
* Only listens to GET requests, and throws an error if it's a POST request.
* This servlet will read the Transactions, and Bookings. Inner-joining them
* on the transaction ID, then it will filter the user booking and return the
* mapped data.
*/

@WebServlet(name = "GetAllBookingServlet", value = "/get/booking/all")
public class GetAllBookingServlet extends HttpServlet {
    // DAO Access
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    // Gson builder with custom adapters for date time serialization
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    // Retrieve data
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.type(LogType.INFO).message("/get/booking/all").print();
        String userId = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Grab the transactions and filter them with the userId
            List<Transaction> userTransactions = transactionDao.findAll().stream()
                    .filter(t -> t.getUserId().equals(userId))
                    .collect(Collectors.toList());
            // Sort the userTransactions so they are in ascending order
            GlobalSort.insertionSortByDates(userTransactions);
            // Grab the bookingId's referenced in the transactions
            Set<String> bookingIds = userTransactions.stream()
                    .map(Transaction::getBookingId)
                    .collect(Collectors.toSet());
            // Fetch all bookings and filter them using the bookingId's retrieved earlier
            List<Booking> userBookings = bookingDao.findAll().stream()
                    .filter(b -> bookingIds.contains(b.getBookingId()))
                    .collect(Collectors.toList());
            // Initiate an empty map to populate the data with
            List<Map<String, Object>> joinedData = new ArrayList<>();
            for (Transaction transaction : userTransactions) {
                // Create a new map, for every entry that's in the transactions that
                // are from the user, retrieve the location, data, amount and date of the booking
                userBookings.stream()
                        .filter(booking -> booking.getBookingId().equals(transaction.getBookingId()))
                        .findFirst()
                        .ifPresent(booking -> {
                            Map<String, Object> entry = new LinkedHashMap<>();
                            LocalDate date = booking.getStartDateTime().toLocalDate();

                            entry.put("amount", transaction.getAmount());
                            entry.put("start", date);
                            try {
                                ParkingSlot slot = parkingSlotDao.getById(booking.getSlotId());
                                entry.put("location", slot.getLocationName());
                                entry.put("slot", slot.getSlotName());
                            } catch (Exception e) {
                                Log.type(LogType.ERROR).message(e.getMessage()).print();
                            }
                            // Add the entry to the Json response
                            joinedData.add(entry);
                        });
            }
            // Return the retrieved data
            Log.type(LogType.NOTE).message("@/get/booking/list -> finalized data").print();
            response.getWriter().write(gson.toJson(joinedData));
            Log.type(LogType.INFO).message("[/get/booking/list] successfully sent data").print();
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Failed to fetch bookings.\"}");
            Log.type(LogType.ERROR).message("[/get/booking/list] failed to deliver data").print();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }
}