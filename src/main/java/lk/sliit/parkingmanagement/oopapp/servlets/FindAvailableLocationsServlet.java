package lk.sliit.parkingmanagement.oopapp.servlets;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "FindAvailableLocations", value = "/findlocations")
public class FindAvailableLocationsServlet extends HttpServlet {
    private final String filePath = FileConfig.INSTANCE.getSlotPath();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);
        List<ParkingSlot> slots = slotJsonHelper.readAll();
        try {
            Set<String> uniqueLocations = slots.stream()
                    .map(ParkingSlot::getLocation)
                    .filter(loc -> loc != null && !loc.trim().isEmpty())
                    .collect(Collectors.toSet());

            List<Map<String, String>> locationsList = uniqueLocations.stream()
                    .map(loc -> Map.of("id", loc, "name", loc))
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
