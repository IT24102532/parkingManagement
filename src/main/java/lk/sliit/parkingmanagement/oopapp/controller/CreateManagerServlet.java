package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.dao.ManagerDao;
import lk.sliit.parkingmanagement.oopapp.dao.ManagerDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.Manager;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;

@WebServlet(name = "CreateManagerServlet", value = "/post/manager/add")
public class CreateManagerServlet extends HttpServlet {
    private final String file = FileConfig.INSTANCE.getManagerPath();
    private final ManagerDao managerDao = new ManagerDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported for this endpoint.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Manager data = new Gson().fromJson(request.getReader(), Manager.class);

            if (data == null || data.getName() == null || data.getName().isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Manager name is required.");
                return;
            }

            managerDao.create(data);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Manager created successfully\"}");
        } catch (Exception e) {
            Log.type(LogType.ERROR).message(e.getMessage()).print();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create manager.");
        }
    }
}
