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
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
/* Servlet Designed for retrieve recent bookings and user registrations
* as a JSON response for the admin dashboard
*
* Only listens to GET requests and returns an error for POST requests
*
* This servlet reads Booking,Transaction,ParkingSlot and User Data
* It filters bookings made during the current week and fetches the latest users
* The resulting data is mapped and returned as a structured JSON object
 */

//Retrieve Data
@WebServlet(name = "GetAdminDashData", value = "/get/data/recent")
public class GetAdminDashData extends HttpServlet {
    //DAO Access
    private final UserDao userDao = new UserDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");

        //Calculate Start of week
        LocalDateTime startOfWeek = LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();
        //Retrieve Recent bookings data
        List<Booking> recentBookings = bookingDao.getAllBookings().stream()
                .filter(b -> b.getCreatedAt().isAfter(startOfWeek))
                .sorted(Comparator.comparing(Booking::getCreatedAt).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<User> recentUsers;
        try {
            //Retrieve the ten most recent users by sorting the users by their registration date in descending order
            recentUsers = userDao.findAll().stream()
                    .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Failed to retrieve user data
            Log.type(LogType.ERROR).message("Failed at GetAdminDashData @LN 58\n" + e.getMessage()).print();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve users.");
            return;
        }
        // Initiate an empty map to populate the data with
        List<Map<String, Object>> bookingData = new ArrayList<>();
        for (Booking booking : recentBookings) {
            // Create a new map, to store booking information
            // Add formatted creation and start date/time to the map
            Map<String, Object> entry = new HashMap<>();
            entry.put("date", formatDateTime(booking.getCreatedAt()));
            entry.put("start", formatDateTime(booking.getStartDateTime()));
            try {
                //Retrieve the transactions and parking slot details
                Transaction tx = transactionDao.getByBookingId(booking.getBookingId());
                ParkingSlot slot = parkingSlotDao.getById(booking.getSlotId());
                //Adding transaction amount and parking slot details to the map
                entry.put("amount", tx.getAmount());
                entry.put("location", slot.getLocationName());
                entry.put("slotName", slot.getSlotName());
            } catch (Exception e) {
                //Failed to retrieve transactions and slots data
                Log.type(LogType.ERROR).message("Failed to retrieve transactions @GetAdminDashData").print();
                entry.put("amount", "N/A");
            }
            //Add the entry to the JSON response
            bookingData.add(entry);
        }

        //Initiate an empty map to populate the data with
        List<Map<String, Object>> userData = new ArrayList<>();
        for (User user : recentUsers) {
            //Create a new map, to hold individual user data
            //Add user's full name and formatted registration date to the map
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", user.getFirstName() + " " + user.getLastName());
            entry.put("joined", formatDateTime(user.getCreatedAt()));
            //Adding the completed data to UserData list
            userData.add(entry);
        }
        //format the data and send the response
        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("bookings", bookingData);
        finalResponse.put("users", userData);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().create();
        response.getWriter().write(gson.toJson(finalResponse));
        Log.type(LogType.SUCCESS).message("Admin Dash Data has been sent").print();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
        Log.type(LogType.ERROR).message("Post method is not supported for this endpoint.").print();
    }
    //date formatter
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd @HH:mm"));
    }
}