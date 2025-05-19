package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDao;
import lk.sliit.parkingmanagement.oopapp.dao.BookingDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
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

/*
*Servlet designed to retrieve the total number of current active bookings
*
* Only listens to GET requests and error for any other method
*
* reads data from the booking table and filter them
* The count of active bookings returned as a JSON object
*
*/
@WebServlet(name = "GetBookingsActive", value = "/get/bookings/active")
public class GetBookingsActive extends HttpServlet {
    //DAO Access
    private final UserDao userDao = new UserDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");

        //fetch all bookings filter those haven't been checked out
        List<Booking> bookings = bookingDao.getAllBookings().stream()
                .filter(b -> b.getCheckOutTime() == null)
                .collect(Collectors.toList());
        int count = bookings.size();
        Map<String, Integer> result = new HashMap<>();
        result.put("active", count);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //Converting results map to JSON
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(result));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }
}