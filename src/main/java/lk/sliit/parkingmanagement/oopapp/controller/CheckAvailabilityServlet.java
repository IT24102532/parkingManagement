package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDao;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.LongTermSlot;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "CheckAvailability", value = "/findavailable")
public class CheckAvailabilityServlet extends HttpServlet {
    ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String location = request.getParameter("location");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        // Fetch all slots in the given location
        List<ParkingSlot> availableSlots = parkingSlotDao.getAvailableSlotsByDates(startDate, endDate, location);

        // Convert available slots to JSON and return response
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(availableSlots);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

}
