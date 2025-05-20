package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDao;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.InstaSlot;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
/*
*servlet designed to retrieve available parking slot info
* @parameters to filter , fetching and availability checks
*
* for long term slots actions will perform regarding
* if a slot is available or a slot is not available
*
* for insta slots returns available slots and filters them according
* to location and time
*/

@WebServlet(name = "LocationsByDateServlet", value = "/get/slot")
public class LocationsByDateServlet extends HttpServlet {
    //DAO Access
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

    //Gson instance
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        //retrieve parameters from the request
        String type     = req.getParameter("type");
        String location = req.getParameter("location");
        String start    = req.getParameter("startDate");
        String end      = req.getParameter("endDate");
        String action = req.getParameter("action");

        // Validate type
        if (type == null ||
                !(type.equalsIgnoreCase("long_term") || type.equalsIgnoreCase("insta"))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false, \"error\": \"Invalid or missing 'type' parameter\"}");
            return;
        }

        try {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);

            if (type.equalsIgnoreCase("long_term")) {
                // long_term requires start AND end
                if (start == null || end == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"success\": false, \"error\": \"Missing startDate or endDate\"}");
                    return;
                }

                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate   = LocalDate.parse(end);

                if (location == null || location.isBlank()) {
                    List<ParkingSlot> all = parkingSlotDao.getAvailableSlotsByDates(startDate, endDate);
                    Map<String, String> locMap = all.stream()
                            .collect(Collectors.toMap(
                                    slot -> Optional.ofNullable(slot.getLocation()).orElse("UNKNOWN"),
                                    slot -> Optional.ofNullable(slot.getLocationName()).orElse("Unnamed"),
                                    (first, second) -> first
                            ));
                    jsonResponse.add("locations", gson.toJsonTree(locMap));

                } else {
                    //specific location requested
                    List<ParkingSlot> slots = parkingSlotDao
                            .getAvailableSlotsByDates(startDate, endDate, location);
                    jsonResponse.add("slots", gson.toJsonTree(slots));
                }

            } else {// type == insta
                HttpSession session = req.getSession(true);
                List<ParkingSlot> allInsta = parkingSlotDao.findAll().stream()
                        .filter(slot -> "insta".equalsIgnoreCase(slot.getLotType()))
                        .collect(Collectors.toList());

                if (location == null || location.isBlank()) {
                    Map<String, String> instLocMap = allInsta.stream()
                            .collect(Collectors.toMap(
                                    slot -> Optional.ofNullable(slot.getLocation()).orElse("UNKNOWN"),
                                    slot -> Optional.ofNullable(slot.getLocationName()).orElse("Unnamed"),
                                    (first, second) -> first
                            ));
                    jsonResponse.add("locations", gson.toJsonTree(instLocMap));
                } else {
                    if ("next".equalsIgnoreCase(action)) {
                        handleNextSlot(session, jsonResponse, resp);
                    } else {
                        // Check for 'time' parameter
                        String timeParam = req.getParameter("time");
                        if (timeParam == null || timeParam.isEmpty()) {
                            sendError(resp, "Missing 'time' parameter for Insta slot", HttpServletResponse.SC_BAD_REQUEST);
                            return;
                        }
                        try {
                            LocalTime requestedTime = LocalTime.parse(timeParam);
                            String formattedTime = requestedTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                            handleInitialRequest(session, location, formattedTime, jsonResponse, resp);
                        } catch (DateTimeParseException e) {
                            sendError(resp, "Invalid time format, expected HH:mm", HttpServletResponse.SC_BAD_REQUEST);
                            return;
                        }
                    }
                }
            }

            resp.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            //catch any unhandled exception
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"success\": false, \"error\": \"Internal server error\"}");
        }
    }
    //Handles initial insta slot requests
    private void handleInitialRequest(HttpSession session, String location, String requestedTime, JsonObject jsonResponse, HttpServletResponse resp) throws Exception {
        List<ParkingSlot> slotsByLoc = parkingSlotDao.findAll().stream()
                .filter(slot -> "insta".equalsIgnoreCase(slot.getLotType()))
                .filter(slot -> location.equalsIgnoreCase(slot.getLocation()))
                .filter(slot -> {
                    if (slot instanceof InstaSlot) {
                        InstaSlot instaSlot = (InstaSlot) slot;
                        return !instaSlot.getBookedTimes().contains(requestedTime);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        quickSort(slotsByLoc, 0, slotsByLoc.size() - 1);

        //push into a stack
        List<ParkingSlot> topFive = slotsByLoc.subList(0, Math.min(5, slotsByLoc.size()));
        Stack<ParkingSlot> stack = new Stack<>();
        for (int i = topFive.size() - 1; i >= 0; i--) {
            stack.push(topFive.get(i));
        }

        if (stack.isEmpty()) {
            sendError(resp, "No slots available", HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //send the first slot
        ParkingSlot currentSlot = stack.pop();
        session.setAttribute("slotStack", stack);
        jsonResponse.add("slot", gson.toJsonTree(currentSlot));
        jsonResponse.addProperty("remaining", stack.size());
    }

    //handles "next" action
    private void handleNextSlot(HttpSession session, JsonObject jsonResponse, HttpServletResponse resp) throws IOException {
        Stack<ParkingSlot> stack = (Stack<ParkingSlot>) session.getAttribute("slotStack");
        if (stack == null || stack.isEmpty()) {
            sendError(resp, "No more slots available", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ParkingSlot currentSlot = stack.pop();
        session.setAttribute("slotStack", stack);
        jsonResponse.add("slot", gson.toJsonTree(currentSlot));
        jsonResponse.addProperty("remaining", stack.size());
    }

    //custom quicksort
    private void quickSort(List<ParkingSlot> slots, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(slots, low, high);
            quickSort(slots, low, pivotIndex - 1);
            quickSort(slots, pivotIndex + 1, high);
        }
    }

    //partition logic for quick sort
    private int partition(List<ParkingSlot> slots, int low, int high) {
        ParkingSlot pivot = slots.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(slots.get(j), pivot) <= 0) {
                i++;
                Collections.swap(slots, i, j);
            }
        }
        Collections.swap(slots, i + 1, high);
        return i + 1;
    }

    //custom comparator
    private int compare(ParkingSlot a, ParkingSlot b) {
        int dateCompare = a.getCreatedAt().compareTo(b.getCreatedAt());

        double priceA = (a instanceof InstaSlot) ? ((InstaSlot) a).getPrice() : 0.0;
        double priceB = (b instanceof InstaSlot) ? ((InstaSlot) b).getPrice() : 0.0;

        return dateCompare != 0 ? dateCompare : Double.compare(priceA, priceB);
    }

    //send error responses
    private void sendError(HttpServletResponse resp, String error, int status) throws IOException {
        resp.setStatus(status);
        resp.getWriter().write("{\"success\": false, \"error\": \"" + error + "\"}");
    }
}
