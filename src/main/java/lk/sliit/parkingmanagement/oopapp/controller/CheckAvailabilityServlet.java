package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "CheckAvailability", value = "/findavailable")
public class CheckAvailabilityServlet extends HttpServlet {
    private final String filePath = FileConfig.INSTANCE.getSlotPath();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);

        String location = request.getParameter("location");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        // Fetch all slots in the given location
        List<ParkingSlot> slotsInLocation = slotJsonHelper.findAll(slot -> location.equals(slot.getLocation()));

        // Filter available slots
        List<ParkingSlot> availableSlots = slotsInLocation.stream()
                .filter(ParkingSlot::isAvailable)
                .filter(slot -> isSlotAvailable(slot, startDate, endDate))
                .collect(Collectors.toList());

        // Convert available slots to JSON and return response
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(availableSlots);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    private boolean isSlotAvailable(ParkingSlot slot, LocalDate startDate, LocalDate endDate) {
        for (String booked : slot.getBookedDates()) {
            LocalDate bookedDate = LocalDate.parse(booked.substring(0, 10));
            if (!startDate.isAfter(bookedDate) && !endDate.isBefore(bookedDate)) {
                return false;
            }
        }
        return true;
    }
}
