package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDao;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/*
*Servlet designed to retrieve detailed info about Long term parking slots
* param id - id of the parking slots to be retrieved
*
* this servlet reads data from parkingSlot using provided id
*/

@WebServlet(name = "LongTermSlotsGetServlet", value = "/get/slot/id")
public class LongTermSlotsGetServlet extends HttpServlet {
    //DAO Access
    private final Logger LOGGER = Logger.getLogger(LongTermSlotsGetServlet.class.getName());
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        //create  a Gson with a custom adapter for LocalDateTime
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ParkingSlot slot;
        try {
            slot = parkingSlotDao.getById(id);
        } catch (Exception e) {
            //Handle server side exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": false, \"error\": \"Internal server error.\"}");
            return;
        }

        //If no slot found to the given id, return error 404
        if (slot == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": false, \"error\": \"Slot not found!\"}");
            return;
        }
        //if the slot is successfully retrieved,return its details as a JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(gson.toJson(slot));
    }


}