package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import com.google.gson.Gson;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
/*
*Servlet designs to retrieve the most recent booking made by a user
*
* param id - to fetch the user transactions and associated bookings
*
* Reads data from transaction and booking
* first filters all transactions by user ID to collect booking IDs,
* and retrieves bookings to those IDs
*
* after sorting bookings , the most
* recent booking is selected and returned in JSON format
*/

@WebServlet(name = "RecentBookingInfoServlet", value = "/get/booking/recent")
public class RecentBookingInfoServlet extends HttpServlet {
    //DAO Access
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        System.out.println(userId);
        if (userId == null) {
            response.sendError(400, "Missing user id");
            return;
        }

        //  Fetch all transactions for user, grab their booking IDs
        List<String> bookingIds = null;
        try {
            bookingIds = transactionDao.findAll().stream()
                    .filter(t -> t.getUserId().equals(userId))
                    .map(Transaction::getBookingId)
                    .distinct()
                    .collect(Collectors.toList());
            System.out.println("found bookings");
            System.out.println(bookingIds);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (bookingIds.isEmpty()) {
            response.sendError(404, "No bookings found");
            return;
        }

        //  Fetch all those bookings in one go
        List<Booking> bookings = null;
        try {
            List<String> finalBookingIds = bookingIds;
            bookings = bookingDao.findAll().stream()
                    .filter(b -> finalBookingIds.contains(b.getBookingId()))
                    .collect(Collectors.toList());
            System.out.println("reached bookings");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //  Sort by createdAt, pick the most recent
        insertionSortByCreatedAt(bookings);
        Booking recent = bookings.get(bookings.size() - 1);

        // Return it
        response.setContentType("application/json");
        System.out.println("writing recent booking");
        response.setCharacterEncoding("UTF-8");
        System.out.println("sent data");
        response.getWriter().write(gson.toJson(recent));
    }

    private void insertionSortByCreatedAt(List<Booking> list) {
        for (int i = 1; i < list.size(); i++) {
            Booking key = list.get(i);
            LocalDateTime kt = key.getCreatedAt();
            int j = i - 1;
            while (j >= 0 && list.get(j).getCreatedAt().isAfter(kt)) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}
