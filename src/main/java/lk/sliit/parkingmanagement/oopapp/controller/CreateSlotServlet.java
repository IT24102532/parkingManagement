package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.dao.ManagerDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDao;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.InstaSlot;
import lk.sliit.parkingmanagement.oopapp.model.LongTermSlot;
import lk.sliit.parkingmanagement.oopapp.model.Manager;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@WebServlet(name = "CreateSlotServlet", value = "/post/slot/create")
public class CreateSlotServlet extends HttpServlet {
    private final ParkingSlotDao slotDao = new ParkingSlotDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported for this endpoint.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();

            String lotType = jsonObject.get("lotType").getAsString();
            String location = jsonObject.get("location").getAsString();
            String locationName = jsonObject.get("locationName").getAsString();
            String lotManager = jsonObject.get("lotManager").getAsString();
            String slotName = jsonObject.get("slotName").getAsString();
            double charge = jsonObject.get("charge").getAsDouble();
            double overstayCharge = jsonObject.get("overstayCharge").getAsDouble();

            ParkingSlot slot;

            if ("long_term".equalsIgnoreCase(lotType)) {
                slot = new LongTermSlot(location, lotManager, lotType, locationName, slotName, true, charge, overstayCharge);
            } else if ("insa".equalsIgnoreCase(lotType)) {
                slot = new InstaSlot(location, lotManager, lotType, locationName, slotName, true, charge, 1, overstayCharge);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown lotType: " + lotType);
                return;
            }
            try {
                slotDao.create(slot);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save slot");
                response.setContentType("application/json");
                response.getWriter().write("{\"status\":\"fail\"}");
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            Log.type(LogType.ERROR).message(e.getMessage()).print();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save slot");
        }
    }
}
