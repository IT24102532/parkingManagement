package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.model.PaymentDetails;
import lk.sliit.parkingmanagement.oopapp.model.Vehicle;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final Logger LOGGER = Logger.getLogger(SignUpServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String step = request.getParameter("step");

        try {
            if ("user".equalsIgnoreCase(step)) {
                // Step 1: Collect user details
                String fName = request.getParameter("f_name");
                String lName = request.getParameter("l_name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                String userUuid = UUID.randomUUID().toString();
                String hashedPassword = PasswordHasher.hashPassword(password);

                session.setAttribute("user_uuid", userUuid);
                session.setAttribute("f_name", fName);
                session.setAttribute("l_name", lName);
                session.setAttribute("email", email);
                session.setAttribute("hashedPassword", hashedPassword);

                request.getRequestDispatcher("/views/vehicleDetails.jsp").forward(request, response);

            } else if ("vehicle".equalsIgnoreCase(step)) {
                // Step 2: Collect vehicle details
                String carType = request.getParameter("carType");
                String regLocation = request.getParameter("regLocation");
                String regState = request.getParameter("regState");
                String licensePlate = request.getParameter("licensePlate");

                session.setAttribute("carType", carType);
                session.setAttribute("regLocation", regLocation);
                session.setAttribute("regState", regState);
                session.setAttribute("licensePlate", licensePlate);

                request.getRequestDispatcher("/views/paymentDetails.jsp").forward(request, response);

            } else if ("payment".equalsIgnoreCase(step)) {
                // Step 3: Final registration with payment
                String cardHolder = request.getParameter("cardHolder");
                String cardNumber = request.getParameter("cardNumber");
                String expiry = request.getParameter("expiry");
                int cvv = Integer.parseInt(request.getParameter("cvv"));

                // Create payment details
                PaymentDetails paymentDetails = new PaymentDetails(cardHolder, cardNumber, expiry, cvv);

                // Create vehicle details
                String carType = (String) session.getAttribute("carType");
                String regLocation = (String) session.getAttribute("regLocation");
                String regState = (String) session.getAttribute("regState");
                String licensePlate = (String) session.getAttribute("licensePlate");

                Vehicle vehicle = new Vehicle(carType, regLocation, regState, licensePlate);

                // Create customer object
                Customer customer = new Customer(
                        (String) session.getAttribute("user_uuid"),
                        (String) session.getAttribute("f_name"),
                        (String) session.getAttribute("l_name"),
                        (String) session.getAttribute("email"),
                        (String) session.getAttribute("hashedPassword"),
                        "user",
                        new ArrayList<>(),
                        vehicle,
                        paymentDetails
                );

                try {
                    userDao.create(customer);
                    session.setAttribute("user", customer);
                    request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Failed to create user", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save customer.");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong.");
        }
    }
}
