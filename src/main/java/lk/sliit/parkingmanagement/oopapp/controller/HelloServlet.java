package lk.sliit.parkingmanagement.oopapp.controller;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.*;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;

@WebServlet(name = "test", value = "/test1")
public class HelloServlet extends HttpServlet {
    private String message;
    private String userFilePath = FileConfig.INSTANCE.getUsersPath();

    public void init() {
        message = "User Doesn't exist";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        System.out.println("Searching for email: " + email);

        if (email != null) {
            JsonHelper<User> userJsonHelper = new JsonHelper<>(userFilePath, User.class);
            List<User> users =    userJsonHelper.readAll();
            System.out.println("Total users in file: " + users.size());
            User user = userJsonHelper.findOne(user1 -> user1.getEmail().equalsIgnoreCase(email));
            System.out.println("User found: " + (user != null ? user.toString() : "null"));

            if (user != null) {
                System.out.println("Actual class of user: " + user.getClass().getSimpleName());
                if (user instanceof Customer) {
                    request.setAttribute("user", (Customer) user);  // Cast User to Customer
                    System.out.println("Forwarding a Customer object to JSP");
                } else {
                    request.setAttribute("user", user);
                    System.out.println("Forwarding a User object to JSP");
                }
                RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);
            }
            else {
                response.getWriter().println("User Not Found");
            }
        }
        else {
            response.getWriter().println(message);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String userType = request.getParameter("user_type");
        String carType = request.getParameter("carType");
        String licensePlate = request.getParameter("license");
        String passwordHash = PasswordHasher.hashPassword(password);
        String cardNumber = request.getParameter("cardNumber");
        String cardHolder = request.getParameter("cardHolderName");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");

        JsonHelper<User> userHelper = new JsonHelper<User>(userFilePath, User.class);
//        PaymentDetails card = new PaymentDetails(cardNumber, expiryDate ,cardHolder,cvv);

        System.out.println("Absolute path: " + new File(userFilePath).getAbsolutePath());

        List<User> users = userHelper.readAll();
        int userId = users.stream()
                .map(u -> {
                    try {
                        return Integer.parseInt(u.getUserId().substring(1));
                    } catch (NumberFormatException | NullPointerException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0) + 1;


        if (userHelper.findOne(user -> user.getEmail().equalsIgnoreCase(email)) != null) {
            System.out.println("User already exists");
        } else if (userType.equalsIgnoreCase("user")) {
//            Customer newUser = new Customer(username, email, ("U" + userId), passwordHash, carType, licensePlate, card);
//            userHelper.create(newUser);
        } else if (userType.equalsIgnoreCase("admin")) {
//            Admin newAdmin = new Admin(username, email, ("A" + userId), passwordHash);
//            userHelper.create(newAdmin);
        }
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        response.sendRedirect(request.getContextPath() + "/test?email=" + email);
    }

    public void destroy() {}

    @WebServlet(name = "BookSlotServlet", value = "/book")
    public static class BookSlotServlet extends HttpServlet {
        private final String filePath = FileConfig.INSTANCE.getSlotPath();

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);
            BufferedReader reader = request.getReader();
            JsonObject jsonRequest = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject jsonResponse = new JsonObject();

            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"success\": false, \"error\": \"User not logged in!\"}");
                return;
            }
            User user = (User) session.getAttribute("user");
            String userID = user.getUserId();

            String slotID = jsonRequest.has("slotID") ? jsonRequest.get("slotID").getAsString() : null;
            String lotType = jsonRequest.has("lotType") ? jsonRequest.get("lotType").getAsString() : null;

            if (slotID == null || lotType == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"error\": \"Missing required fields!\"}");
                return;
            }

            // Fetch the Parking Slot
            ParkingSlot selectedSlot = slotJsonHelper.findOne(slot -> slot.getParkingSlotID().equalsIgnoreCase(slotID));

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
                totalCharge = longTermSlot.getPricePerDay() * totalDays;

                // Check for overlapping dates
                for (String booked : selectedSlot.getBookedDates()) {
                    LocalDate bookedDate = LocalDate.parse(booked);
                    if (!(endDate.isBefore(bookedDate) || startDate.isAfter(bookedDate))) {
                        response.setStatus(HttpServletResponse.SC_CONFLICT);
                        response.getWriter().write("{\"success\": false, \"error\": \"Slot is already booked for selected dates!\"}");
                        return;
                    }
                }

                // Add booked dates
                updatedBookedDates = new ArrayList<>(selectedSlot.getBookedDates());
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
                totalCharge = instaSlot.getPricePerHour() * durationHours;

                // Check for overlapping bookings
                for (String booked : selectedSlot.getBookedDates()) {
                    LocalDateTime bookedTime = LocalDateTime.parse(booked);
                    if (!(endTime.isBefore(bookedTime) || startTime.isAfter(bookedTime))) {
                        response.setStatus(HttpServletResponse.SC_CONFLICT);
                        response.getWriter().write("{\"success\": false, \"error\": \"Slot is already booked for selected time!\"}");
                        return;
                    }
                }

                // Add booked times
                updatedBookedTimes = new ArrayList<>(selectedSlot.getBookedDates());
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
}