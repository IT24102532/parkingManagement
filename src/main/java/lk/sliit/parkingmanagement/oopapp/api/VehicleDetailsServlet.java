package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dto.UserDTO;
import lk.sliit.parkingmanagement.oopapp.dto.VehicleDto;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.model.Vehicle;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "VehicleDetailsServlet", value = "/get/vehicle")
public class VehicleDetailsServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final Logger LOGGER = Logger.getLogger(VehicleDetailsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user");


        User user = null;
        try {
            user = userDao.getById(userId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();

        VehicleDto vehicleDto = new VehicleDto();
        assert user != null;
        Vehicle vehicle;
        vehicleDto.setUserId(user.getUserId());
        if (user instanceof Customer) {
            vehicle = (((Customer) user).getVehicle());
            vehicleDto.setVehicleType(vehicle.getVehicleType());
            vehicleDto.setVehicleId(vehicle.getVehicleId());
            vehicleDto.setRegNumber(vehicle.getRegNumber());
        }

        String json = gson.toJson(vehicleDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}