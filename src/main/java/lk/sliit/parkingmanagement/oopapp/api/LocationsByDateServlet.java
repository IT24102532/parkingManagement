package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDao;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "LocationsByDateServlet", value = "/get/slot")
public class LocationsByDateServlet extends HttpServlet {
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String type = req.getParameter("type");
        String location = req.getParameter("location");
        String start = req.getParameter("startDate");
        String end = req.getParameter("endDate");

        if (type == null || !type.equalsIgnoreCase("long_term") || start == null || end == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false, \"error\": \"Missing or invalid parameters!\"}");
            return;
        }

        try {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            if (location == null || location.isEmpty()) {
                List<ParkingSlot> availableSlots = parkingSlotDao.getAvailableSlotsByDates(startDate, endDate);
                System.out.println(availableSlots);
                Map<String, String> locationMap = availableSlots.stream()
                        .collect(Collectors.toMap(
                                slot -> Optional.ofNullable(slot.getLocation()).orElse("UNKNOWN"),
                                slot -> Optional.ofNullable(slot.getLocationName()).orElse("Unnamed"),
                                (existing, replacement) -> existing
                        ));
                System.out.println(locationMap);
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("success", true);
                jsonResponse.add("locations", new Gson().toJsonTree(locationMap));
                resp.getWriter().write(jsonResponse.toString());
            } else {
                List<ParkingSlot> availableSlots = parkingSlotDao.getAvailableSlotsByDates(startDate, endDate, location);
                System.out.println(availableSlots);
                List<String> slotIds = availableSlots.stream()
                        .map(ParkingSlot::getSlotId)
                        .collect(Collectors.toList());
                System.out.println(slotIds.size());
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("success", true);
                jsonResponse.add("slots", new Gson().toJsonTree(slotIds));
                resp.getWriter().write(jsonResponse.toString());
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"success\": false, \"error\": \"Internal server error!\"}");
        }
    }
}