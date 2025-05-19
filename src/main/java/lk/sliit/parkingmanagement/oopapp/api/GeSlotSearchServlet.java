package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.*;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
*servlet designed to handle multiple GET endpoints related to parking slot data
* for admin panels or client-side slot management features.
*
* Only listens to GET requests and returns an error for POST requests.
*
*This servlet supports several endpoints
*       -get/slot/all : retrieving all parking slots
*       -get/slot/search : Searching slots
*       -get/slots/locations : Returns a list of all slot locations
*       -get/slot/manager : Returns a list of all registered slot users
*
* Maps the results into structured JSON responses and handles errors
*
*/

@WebServlet(name = "GetSlotSearchServlet", value = {"/get/slot/all", "/get/slot/search", "/get/slot/locations", "/get/slot/manager"})
public class GeSlotSearchServlet extends HttpServlet {
    //DAO Access
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    private final ManagerDao managerDao = new ManagerDaoImpl();
    private final Logger LOGGER = Logger.getLogger(GeSlotSearchServlet.class.getName());

    //convert LocalDateTime to readable string format by FORMATTER
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd @HH:mm");

    //Using custom adaptors 
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> result;

        try {
            result = switch (request.getServletPath()) {
                case "/get/slot/all" -> handleAllSlots(request);
                case "/get/slot/search" -> handleSlotSearch(request);
                case "/get/slot/locations" -> handleSlotLocations(request);
                case "/get/slot/manager" -> handleManagerRequest(request);
                default -> Map.of("status", "error", "message", "Invalid endpoint");
            };
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            result = Map.of("status", "error", "message", "Internal server error");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }

    private Map<String, Object> handleSlotSearch(HttpServletRequest request) {
        String param = Optional.ofNullable(request.getParameter("param")).orElse("").trim().toLowerCase();
        Map<String, Object> result = new HashMap<>();

        if (param.isEmpty()) {
            result.put("error", "Missing or empty search parameter");
            return result;
        }

        List<ParkingSlot> matchedSlots = null;
        try {
            matchedSlots = parkingSlotDao.findAll().stream()
                    .filter(slot ->
                            (slot.getLocationName() + " " + slot.getSlotName()).toLowerCase().contains(param)
                    )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Map<String, Object>> mapped = matchedSlots.stream()
                .map(this::safeMapSlot)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        result.put("slots", mapped);
        return result;
    }

    private Map<String, Object> handleSlotLocations(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<String> locationNames = parkingSlotDao.findAll().stream()
                    .map(ParkingSlot::getLocationName)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            result.put("locations", locationNames);
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "Failed to fetch slot locations");
            Log.type(LogType.ERROR).message(e.getMessage()).print();
        }
        return result;
    }

    private Map<String, Object> handleManagerRequest(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<Map<String, Object>> managersList = new ArrayList<>();
            List<Manager> managers = managerDao.findAll().stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted(Comparator.comparing(Manager::getName))
                    .collect(Collectors.toList());

            for (Manager manager : managers) {
                Map<String, Object> managerMap = new HashMap<>();
                managerMap.put("id", manager.getManagerId());
                managerMap.put("name", manager.getName());
                managersList.add(managerMap);
            }

            result.put("managers", managersList);
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "Failed to fetch slot managers");
            Log.type(LogType.ERROR).message(e.getMessage()).print();
        }

        return result;
    }


    private Map<String, Object> handleAllSlots(HttpServletRequest request) {
        List<ParkingSlot> slots = null;
        try {
            slots = parkingSlotDao.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Sort by created date (newest first)
        slots.sort(Comparator.comparing(ParkingSlot::getCreatedAt).reversed());

        String limitParam = request.getParameter("limit");
        if (limitParam != null) {
            try {
                int limit = Integer.parseInt(limitParam);
                if (limit > 0 && limit < slots.size()) {
                    slots = slots.subList(0, limit);
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid limit parameter: " + limitParam + " from " + request.getRemoteAddr());
            }
        }

        List<Map<String, Object>> mappedSlots = slots.stream()
                .map(this::safeMapSlot)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return Map.of("slots", mappedSlots);
    }

    private Map<String, Object> safeMapSlot(ParkingSlot slot) {
        try {
            return mapSlot(slot);
        } catch (Exception e) {
            LOGGER.warning("Failed to map slot ID " + slot.getSlotId() + ": " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> mapSlot(ParkingSlot slot) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("id", slot.getSlotId());
        entry.put("availability", slot.isAvailable());
        entry.put("location", slot.getLocationName());
        entry.put("slot", slot.getSlotName());
        entry.put("manager", slot.getLocationName());

        if (slot instanceof InstaSlot) {
            entry.put("amount", ((InstaSlot) slot).getPrice());
        } else if (slot instanceof LongTermSlot) {
            entry.put("amount", ((LongTermSlot) slot).getPrice());
        }

        return entry;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }
}
