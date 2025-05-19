package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.AdminLogDao;
import lk.sliit.parkingmanagement.oopapp.dao.AdminLogDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.AdminLogs;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    This servlet handles admin operations for managing user accounts by admin
    It has two main functionalities:
        - Ban a user
        - Edit user details

    Only listen for POST requests
 */

@WebServlet(name = "AdminEditServlet", value = "/post/user/*")
public class AdminEditUserServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final AdminLogDao adminlog = new AdminLogDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported for this endpoint.");
        Log.type(LogType.INFO).message("Get request not allowed");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.matches("^/[^/]+/ban$")) {
            String[] tokens = pathInfo.split("/");
            String userId = tokens[1];

            String adminId = request.getParameter("admin");
            String approvedParam = request.getParameter("approved");
            boolean approved = "true".equalsIgnoreCase(approvedParam);

            if (adminId == null || !approved) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized or malformed request.");
                return;
            }

            try {
                userDao.ban(userId);

                AdminLogs log = new AdminLogs();
                log.setAdminId(adminId);
                log.setAction("{\"action\":\"BAN_USER\",\"admin\":\"" + adminId + "\",\"target\":\"" + userId + "\"}");

                try {
                    adminlog.create(log);
                } catch (Exception e) {
                    Log.type(LogType.ERROR).message("Couldn't create admin log: " + e.getMessage()).print();
                }

                Map<String, String> result = new HashMap<>();
                result.put("action", "BAN_USER");
                result.put("admin", adminId);
                result.put("target", userId);
                result.put("status", "success");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(new Gson().toJson(result));

            } catch (Exception e) {
                Log.type(LogType.ERROR).message("Ban failed: " + e.getMessage()).print();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ban operation failed.");
            }
        } else if (pathInfo != null && pathInfo.matches("^/[^/]+/edit$")) {
            String[] tokens = pathInfo.split("/");
            String userId = tokens[1];

            BufferedReader reader = request.getReader();
            JsonObject jsonBody = JsonParser.parseReader(reader).getAsJsonObject();

            String adminId = jsonBody.has("adminId") ? jsonBody.get("adminId").getAsString() : null;

            if (adminId == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized request: adminId required.");
                return;
            }

            try {
                User user = userDao.getById(userId);
                if (user == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                    return;
                }

                Map<String, Object> updates = new HashMap<>();
                if (jsonBody.has("username")) updates.put("username", jsonBody.get("username").getAsString());
                if (jsonBody.has("email")) updates.put("email", jsonBody.get("email").getAsString());
                if (jsonBody.has("location")) updates.put("location", jsonBody.get("location").getAsString());
                if (jsonBody.has("firstname")) updates.put("f_name", jsonBody.get("firstname").getAsString());
                if (jsonBody.has("lastname")) updates.put("l_name", jsonBody.get("lastname").getAsString());

                userDao.partialUpdate(u -> u.getUserId().equals(userId), updates);

                AdminLogs log = new AdminLogs(adminId, String.format(
                        "{\"action\":\"EDIT_USER\",\"admin\":\"%s\",\"target\":\"%s\"}", adminId, userId
                ));
                try {
                    adminlog.create(log);
                } catch (Exception e) {
                    Log.type(LogType.ERROR).message("Couldn't create admin log: " + e.getMessage()).print();
                }

                Map<String, String> result = new HashMap<>();
                result.put("action", "EDIT_USER");
                result.put("admin", adminId);
                result.put("target", userId);
                result.put("status", "success");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(new Gson().toJson(result));

            } catch (Exception e) {
                Log.type(LogType.ERROR).message("User edit failed: " + e.getMessage()).print();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Edit operation failed.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid endpoint.");
        }
    }
}