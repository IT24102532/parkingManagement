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
            //Determine the requested endpoint and assign to handler method
            result = switch (request.getServletPath()) {
                case "/get/slot/all" -> handleAllSlots(request);
                case "/get/slot/search" -> handleSlotSearch(request);
                case "/get/slot/locations" -> handleSlotLocations(request);
                case "/get/slot/manager" -> handleManagerRequest(request);
                default -> Map.of("status", "error", "message", "Invalid endpoint");
            };
        } catch (Exception e) {
            //Log errors and prepare and error response
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            result = Map.of("status", "error", "message", "Internal server error");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //writing data map as a JSON response
        response.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }

    //Handling the /get/slot/search endpoint to find slots
    private Map<String, Object> handleSlotSearch(HttpServletRequest request) {
        //Getting the search parameter from the request,trim and convert into lowercase
        String param = Optional.ofNullable(request.getParameter("param")).orElse("").trim().toLowerCase();
        Map<String, Object> result = new HashMap<>();

        //If the search parameter missing or empty return the result
        if (param.isEmpty()) {
            result.put("error", "Missing or empty search parameter");
            return result;
        }

        List<ParkingSlot> matchedSlots = null;
        try {
            //fetch all parking slots and filter the slots contains the search keyword
            matchedSlots = parkingSlotDao.findAll().stream()
                    .filter(slot ->
                            (slot.getLocationName() + " " + slot.getSlotName()).toLowerCase().contains(param)
                    )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            Log.type(LogType.ERROR).message("Error processing request"+e.getMessage()).print();
            throw new RuntimeException(e);
        }

        //conver matched slots to a list of safe and mapped representations
        List<Map<String, Object>> mapped = matchedSlots.stream()
                .map(this::safeMapSlot)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        //add the mapped slot into result
        result.put("slots", mapped);
        return result;
    }

    private Map<String, Object> handleSlotLocations(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            //fetch all parking slots and their locations
            List<String> locationNames = parkingSlotDao.findAll().stream()
                    .map(ParkingSlot::getLocationName)//getting locations from each slots
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            result.put("locations", locationNames);
        } catch (Exception e) {
            //if failure return the error status
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
            //fetch all managers,eliminate duplicate and sort alphabetically
            List<Manager> managers = managerDao.findAll().stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted(Comparator.comparing(Manager::getName))
                    .collect(Collectors.toList());
            //mapping each manager data structure with ID and name
            for (Manager manager : managers) {
                Map<String, Object> managerMap = new HashMap<>();
                managerMap.put("id", manager.getManagerId());
                managerMap.put("name", manager.getName());
                managersList.add(managerMap);
            }
            //add the list of managers to the result map
            result.put("managers", managersList);
        } catch (Exception e) {
            //In case of error, display the result with error message
            result.put("status", "error");
            result.put("message", "Failed to fetch slot managers");
            Log.type(LogType.ERROR).message(e.getMessage()).print();
        }

        return result;
    }


    private Map<String, Object> handleAllSlots(HttpServletRequest request) {
        List<ParkingSlot> slots = null;
        try {
            //Retrieve all parking slots
            slots = parkingSlotDao.findAll();
        } catch (Exception e) {
            Log.type(LogType.ERROR).message("Failed to fetch slot all").print();
            //if retrieval fails throw a runtime exception
            throw new RuntimeException(e);
        }

        // Sort by created date (newest first)
        slots.sort(Comparator.comparing(ParkingSlot::getCreatedAt).reversed());

        //checking the 'limit' parameter
        String limitParam = request.getParameter("limit");
        if (limitParam != null) {
            try {
                int limit = Integer.parseInt(limitParam);
                if (limit > 0 && limit < slots.size()) {
                    slots = slots.subList(0, limit);
                }
            } catch (NumberFormatException e) {
                //log a warning if the limit parameter is not a valid integer
                LOGGER.warning("Invalid limit parameter: " + limitParam + " from " + request.getRemoteAddr());
            }
        }

        //mapping the each slot while filter out any null mappings
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
            //log the error and return null to skip this slot
            LOGGER.warning("Failed to map slot ID " + slot.getSlotId() + ": " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> mapSlot(ParkingSlot slot) {
        Map<String, Object> entry = new HashMap<>();
        //Basic slot details
        entry.put("id", slot.getSlotId());
        entry.put("availability", slot.isAvailable());
        entry.put("location", slot.getLocationName());
        entry.put("slot", slot.getSlotName());
        entry.put("manager", slot.getLocationName());

        //Adding pricing based on slot type
        if (slot instanceof InstaSlot) {
            entry.put("amount", ((InstaSlot) slot).getPrice());
        } else if (slot instanceof LongTermSlot) {
            entry.put("amount", ((LongTermSlot) slot).getPrice());
        }

        return entry;
    }

    //formats a LocalDateTime object into a readable string using above defined pattern
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }
}
