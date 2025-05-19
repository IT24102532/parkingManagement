package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.dao.ManagerDao;
import lk.sliit.parkingmanagement.oopapp.dao.ManagerDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.Manager;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;
import java.time.LocalDateTime;

/*
    This servlet handles the creation of new manager accounts in the system by the admin
    - Only listen for POST requests at the endpoint /post/manager/add.

 */

@WebServlet(name = "CreateManagerServlet", value = "/post/manager/add")
public class CreateManagerServlet extends HttpServlet {

    //File path for storing manager data
    private final String file = FileConfig.INSTANCE.getManagerPath();

    //DAO implementation for interacting with manager
    private final ManagerDao managerDao = new ManagerDaoImpl();


    //disallow POST request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported for this endpoint.");
        Log.type(LogType.INFO).message("Get request not allowed");
    }

    //Handle POST request to create a new manager
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            double percentage = jsonObject.get("commission").getAsDouble();

            if (name == null || percentage == 0 || jsonObject.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Manager name is required.");
                return;
            }

            //create a new manager
            Manager manager = new Manager(percentage, name);
            managerDao.create(manager);

            //send success response
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Manager created successfully\"}");
        } catch (Exception e) {
            //respond with error if creation fails
            Log.type(LogType.ERROR).message(e.getMessage()).print();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create manager.");
        }
    }
}
