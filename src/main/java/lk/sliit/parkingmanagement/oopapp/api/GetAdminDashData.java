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

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "GetAdminDashData", value = "/get/data/recent")
public class GetAdminDashData extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");

        LocalDateTime startOfWeek = LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();

        List<Booking> recentBookings = bookingDao.getAllBookings().stream()
                .filter(b -> b.getCreatedAt().isAfter(startOfWeek))
                .sorted(Comparator.comparing(Booking::getCreatedAt).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<User> recentUsers;
        try {
            recentUsers = userDao.findAll().stream()
                    .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve users.");
            return;
        }

        List<Map<String, Object>> bookingData = new ArrayList<>();
        for (Booking booking : recentBookings) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("date", formatDateTime(booking.getCreatedAt()));
            entry.put("start", formatDateTime(booking.getStartDateTime()));
            try {
                Transaction tx = transactionDao.getByBookingId(booking.getBookingId());
                ParkingSlot slot = parkingSlotDao.getById(booking.getSlotId());
                entry.put("amount", tx.getAmount());
                entry.put("location", slot.getLocationName());
                entry.put("slotName", slot.getSlotName());
            } catch (Exception e) {
                entry.put("amount", "N/A");
            }
            bookingData.add(entry);
        }

        List<Map<String, Object>> userData = new ArrayList<>();
        for (User user : recentUsers) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", user.getFirstName() + " " + user.getLastName());
            entry.put("joined", formatDateTime(user.getCreatedAt()));
            userData.add(entry);
        }

        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("bookings", bookingData);
        finalResponse.put("users", userData);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().create();
        response.getWriter().write(gson.toJson(finalResponse));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");

    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd @HH:mm"));
    }
}