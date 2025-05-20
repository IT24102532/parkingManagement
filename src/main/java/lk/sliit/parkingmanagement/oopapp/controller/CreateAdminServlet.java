package lk.sliit.parkingmanagement.oopapp.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.Admin;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreateAdminServlet", value = "/create/admin")
public class CreateAdminServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
        String fname = jsonObject.get("fname").getAsString();
        String lname = jsonObject.get("lname").getAsString();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        int clearance = jsonObject.get("clearance").getAsInt();
        String role = jsonObject.get("role").getAsString();

        List<String> bookings = new ArrayList<>();

        String staffPin = jsonObject.has("staffPin") ? jsonObject.get("staffPin").getAsString() : null;
        // TODO: Create a env variable
        final String VALID_PIN = "1111";

        if (staffPin == null || !staffPin.equals(VALID_PIN)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid staff PIN");
            return;
        }

        String hashedPw = PasswordHasher.hashPassword(password);
        Admin admin = new Admin(fname, lname, email, hashedPw, "admin", bookings, clearance, role);

        try {
            userDao.create(admin);
            Log.type(LogType.SUCCESS).message("Successfully added admin" + admin.getUserId()).print();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Admin created successfully\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}