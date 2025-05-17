package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDao;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "GetBookingsCount", value = "/get/bookings/count")
public class GetBookingsCount extends HttpServlet {
    private final BookingDao bookingDao = new BookingDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        // TODO: validation at production to check if the user is an admin

        List<Booking> bookings = bookingDao.getAllBookings();

        Map<LocalDate, Long> grouped = bookings.stream()
                .filter(b -> b.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        b -> b.getCreatedAt().toLocalDate(),
                        Collectors.counting()
                ));

        List<Map<String, Object>> bookingData = new ArrayList<>();
        grouped.forEach((date, count) -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("date", date.toString());
            entry.put("count", count);
            bookingData.add(entry);
        });

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
        response.getWriter().write(gson.toJson(bookingData));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }
}