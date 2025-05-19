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

/*
*Servlet designed  to retrieve vehicle details for a specific customer
*
* @param user - to identify and fetch vehicle details
* Only listens to GET requests and returns vehicle info for the customer
*
* Reads data from the user table using provided user
*
* Vehicle info and the user is mapped into DTO
* and returned as a formatted JSON object
*/

@WebServlet(name = "VehicleDetailsServlet", value = "/get/vehicle")
public class VehicleDetailsServlet extends HttpServlet {
    //DAO Access
    private final UserDao userDao = new UserDaoImpl();
    private final Logger LOGGER = Logger.getLogger(VehicleDetailsServlet.class.getName());

    //handles GET requests to get vehicle details for a specific customer
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user");
        User user = null;
        try {
            user = userDao.getById(userId);
        } catch (Exception e) {
            //log any exceptions during data retrieval
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //create Gson instance with a custom LocalDateTime adaptor
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();

        // prepare a DTO to hold vehicle data for response
        VehicleDto vehicleDto = new VehicleDto();
        assert user != null;
        Vehicle vehicle;
        vehicleDto.setUserId(user.getUserId());
        //if user is a customer retrieve their vehicle
        if (user instanceof Customer) {
            vehicle = (((Customer) user).getVehicle());

            //map vehicle details to the DTO
            vehicleDto.setVehicleType(vehicle.getVehicleType());
            vehicleDto.setVehicleId(vehicle.getVehicleId());
            vehicleDto.setRegNumber(vehicle.getRegNumber());
        }
        String json = gson.toJson(vehicleDto); //convert the DTO to a JSON string

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);//send the JSON response to the client
    }
}