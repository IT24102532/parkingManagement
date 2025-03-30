package lk.sliit.parkingmanagement.oopapp.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.InstaSlot;
import lk.sliit.parkingmanagement.oopapp.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.LongTermSlot;
import lk.sliit.parkingmanagement.oopapp.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookSlotServlet", value = "/book")
public class BookSlotServlet extends HttpServlet {
    private final String filePath = FileConfig.INSTANCE.getSlotPath();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);
        BufferedReader reader = request.getReader();
        JsonObject jsonRequest = JsonParser.parseReader(reader).getAsJsonObject();

        // **Get User ID from Session**
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"error\": \"User not logged in!\"}");
            return;
        }
        String userID = session.getAttribute("userID").toString();

        // **Extract Data from JSON Request**
        String slotID = jsonRequest.has("slotID") ? jsonRequest.get("slotID").getAsString() : null;
        String startDateStr = jsonRequest.has("startDate") ? jsonRequest.get("startDate").getAsString() : null;
        String endDateStr = jsonRequest.has("endDate") ? jsonRequest.get("endDate").getAsString() : null;

        if (slotID == null || startDateStr == null || endDateStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"error\": \"Missing required fields!\"}");
            return;
        }

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        // Fetch the Parking Slot
        ParkingSlot selectedSlot = slotJsonHelper.findOne(slot -> slot.getParkingSlotID().equals(slotID));

        if (selectedSlot == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"success\": false, \"error\": \"Slot not found!\"}");
            return;
        }

        String lotType = selectedSlot.getLotType(); // Ensure this field exists in ParkingSlot

        double totalCharge = 0;

        if ("LongTerm".equalsIgnoreCase(lotType) && selectedSlot instanceof LongTermSlot) {
            LongTermSlot longTermSlot = (LongTermSlot) selectedSlot;
            long totalDays = startDate.until(endDate).getDays() + 1; // Include both start & end dates
            totalCharge = longTermSlot.getPricePerDay() * totalDays;
        }
        else if ("Insta".equalsIgnoreCase(lotType) && selectedSlot instanceof InstaSlot) {
            InstaSlot instaSlot = (InstaSlot) selectedSlot;

            // Assuming startDate and endDate include the time (use LocalDateTime if needed)
            long totalHours = startDate.until(endDate).toTotalMonths() * 30 * 24; // Example: Convert to hours
            totalCharge = instaSlot.getPricePerHour() * totalHours;
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"error\": \"Invalid slot type!\"}");
            return;
        }

        // Check if the Slot is Already Booked in the Given Range
        for (String booked : selectedSlot.getBookedDates()) {
            LocalDate bookedDate = LocalDate.parse(booked);
            if (!(endDate.isBefore(bookedDate) || startDate.isAfter(bookedDate))) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"success\": false, \"error\": \"Slot is already booked for selected dates!\"}");
                return;
            }
        }

        // Update the Booking
        List<String> updatedBookedDates = new ArrayList<>(selectedSlot.getBookedDates());
        LocalDate tempDate = startDate;
        while (!tempDate.isAfter(endDate)) {
            updatedBookedDates.add(tempDate.toString());
            tempDate = tempDate.plusDays(1);
        }
        selectedSlot.setBookedDates(updatedBookedDates);

        // **Save to JSON File**
        slotJsonHelper.update(slot -> slot.getParkingSlotID().equals(slotID), selectedSlot);

        // Forward Booking Details to checkout
        request.setAttribute("slotID", slotID);
        request.setAttribute("userID", userID);
        request.setAttribute("startDate", startDateStr);
        request.setAttribute("endDate", endDateStr);
        request.setAttribute("totalCharge", totalCharge);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout");
        dispatcher.forward(request, response);
    }
}
