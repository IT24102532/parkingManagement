package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "FindAvailableLocations", value = "/findlocations")
public class FindAvailableLocationsServlet extends HttpServlet {
    private final String filePath = FileConfig.INSTANCE.getSlotPath();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);
        List<ParkingSlot> slots = slotJsonHelper.readAll();
        try {
            Map<String, String> locationMap = slots.stream()
                    .filter(slot -> slot.getLocation() != null && !slot.getLocation().trim().isEmpty() && slot.getLocationName() != null && !slot.getLocationName().trim().isEmpty())
                    .collect(Collectors.toMap(ParkingSlot::getLocation, ParkingSlot::getLocationName, (existing, replacing) -> existing));

            List<Map<String, String>> locationsList = locationMap.entrySet().stream()
                    .map(entry -> Map.of("id", entry.getKey(), "name", entry.getValue()))
                    .collect(Collectors.toList());

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(locationsList));
        }
        catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"error\": \"Server error\"}");
            e.printStackTrace();
        }


    }
}
