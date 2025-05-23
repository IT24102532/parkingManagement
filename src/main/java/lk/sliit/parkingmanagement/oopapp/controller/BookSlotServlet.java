package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDao;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.InstaSlot;
import lk.sliit.parkingmanagement.oopapp.model.LongTermSlot;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@WebServlet(name = "BookSlotServlet", value = "/book")
public class BookSlotServlet extends HttpServlet {
    ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    UserDao userDao = new UserDaoImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        JsonObject jsonRequest = JsonParser.parseReader(reader).getAsJsonObject();
        JsonObject jsonResponse = new JsonObject();

        // is user logged in?
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"error\": \"User not logged in!\"}");
            return;
        }
        String userID = (String) session.getAttribute("user");
        try {
            User user = userDao.getById(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String slotID = jsonRequest.has("slotID") ? jsonRequest.get("slotID").getAsString() : null;
        String lotType = jsonRequest.has("lotType") ? jsonRequest.get("lotType").getAsString() : null;

        if (slotID == null || lotType == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"error\": \"Missing required fields!\"}");
            return;
        }

        // Fetch the Parking Slot
        ParkingSlot selectedSlot = null;
        try {
            selectedSlot = parkingSlotDao.getById(slotID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (selectedSlot == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"success\": false, \"error\": \"Slot not found!\"}");
            return;
        }

        double totalCharge = 0;
        List<String> updatedBookedTimes = List.of(), updatedBookedDates = List.of();


        if ("long-term".equalsIgnoreCase(lotType) && selectedSlot instanceof LongTermSlot) {
            // Handle Long-Term Slot Booking
            String startDateStr = jsonRequest.has("startDate") ? jsonRequest.get("startDate").getAsString() : null;
            String endDateStr = jsonRequest.has("endDate") ? jsonRequest.get("endDate").getAsString() : null;

            if (startDateStr == null || endDateStr == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"error\": \"Missing required fields!\"}");
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            LongTermSlot longTermSlot = (LongTermSlot) selectedSlot;
            long totalDays = startDate.until(endDate).getDays() + 1;
            totalCharge = longTermSlot.getPrice() * totalDays;

            // Check for overlapping dates
            for (String booked : ((LongTermSlot) selectedSlot).getBookedDates()) {
                LocalDate bookedDate = LocalDate.parse(booked);
                if (!(endDate.isBefore(bookedDate) || startDate.isAfter(bookedDate))) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getWriter().write("{\"success\": false, \"error\": \"Slot is already booked for selected dates!\"}");
                    return;
                }
            }

            // Add booked dates
            updatedBookedDates = new ArrayList<>(((LongTermSlot) selectedSlot).getBookedDates());
            LocalDate tempDate = startDate;
            while (!tempDate.isAfter(endDate)) {
                updatedBookedDates.add(tempDate.toString());
                tempDate = tempDate.plusDays(1);
            }
        }
        else if ("insta".equalsIgnoreCase(lotType) && selectedSlot instanceof InstaSlot) {
            // Handle Insta Slot Booking
            String startTimeStr = jsonRequest.has("startTime") ? jsonRequest.get("startTime").getAsString() : null;
            int durationHours = jsonRequest.has("durationHours") ? jsonRequest.get("durationHours").getAsInt() : 1;

            if (startTimeStr == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"error\": \"Missing required fields for InstaSlot!\"}");
                return;
            }

            LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
            LocalDateTime endTime = startTime.plusHours(durationHours);

            InstaSlot instaSlot = (InstaSlot) selectedSlot;
            totalCharge = instaSlot.getPrice() * durationHours;

            // Check for overlapping bookings
            for (String booked : ((InstaSlot) selectedSlot).getBookedTimes()) {
                LocalDateTime bookedTime = LocalDateTime.parse(booked);
                if (!(endTime.isBefore(bookedTime) || startTime.isAfter(bookedTime))) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getWriter().write("{\"success\": false, \"error\": \"Slot is already booked for selected time!\"}");
                    return;
                }
            }

            // Add booked times
            updatedBookedTimes = new ArrayList<>(((InstaSlot) selectedSlot).getBookedTimes());
            LocalDateTime tempTime = startTime;
            while (!tempTime.isAfter(endTime)) {
                updatedBookedTimes.add(tempTime.toString());
                tempTime = tempTime.plusHours(1);
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"error\": \"Invalid slot type!\"}");
            return;
        }

        // Set booking details in request attributes
        request.setAttribute("slotID", slotID);
        request.setAttribute("lotType", lotType);
        request.setAttribute("userId", userID);
        request.setAttribute("totalCharge", totalCharge);

        if (lotType.equalsIgnoreCase("insta")) {
            request.setAttribute("bookedHours", updatedBookedTimes);
        } else {
            request.setAttribute("bookedDates", updatedBookedDates);
        }

        // Forward to checkout page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout");
        dispatcher.forward(request, response);

    }

}
