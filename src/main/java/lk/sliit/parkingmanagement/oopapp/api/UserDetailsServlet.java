package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.dto.UserDTO;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
*Servlet designed to retrieve detailed user info and return it
*
* @param user - to fetch the corresponding user details
*Only listens to GET requests and returns user profile data.
*
*user id maps basic user information into user DTO object
*
*final user data is serialized into a JSON object and returned to the client.
*/
@WebServlet(name = "UserDetailsServlet", value = "/get/user")
public class UserDetailsServlet extends HttpServlet {
    //DAO Access
    private final UserDao userDao = new UserDaoImpl();
    private final Logger LOGGER = Logger.getLogger(UserDetailsServlet.class.getName());

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

        // prepare a DTO to hold user data for response
        UserDTO userDTO = new UserDTO();
        assert user != null;
        userDTO.setF_name(user.getFirstName());
        userDTO.setL_name(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUser_uuid(user.getUserId());

        //if retrieved user is an instance of Customer
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            //map vehicle and payment details into DTO
            userDTO.setVehicle(customer.getVehicle());
            userDTO.setPaymentDetails(customer.getPaymentDetails());
        }


        String gsonRes = gson.toJson(userDTO);//convert the DTO to a JSON string

        response.setContentType("application/json");
        response.getWriter().write(gsonRes);//Write the JSON response to the output stream
    }
}