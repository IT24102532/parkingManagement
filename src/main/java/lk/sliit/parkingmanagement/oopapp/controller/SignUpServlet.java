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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();

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

                // Generate UUID and hash password
                String userUuid = UUID.randomUUID().toString();
                String hashedPassword = PasswordHasher.hashPassword(password);

                // Store in session
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

                // Store the details
                session.setAttribute("carType", carType);
                session.setAttribute("regLocation", regLocation);
                session.setAttribute("regState", regState);
                session.setAttribute("licensePlate", licensePlate);

                request.getRequestDispatcher("/views/paymentDetails.jsp").forward(request, response);

            } else if ("payment".equalsIgnoreCase(step)) {
                // Step 3: Collect payment details and create user
                String cardHolder = request.getParameter("cardHolder");
                String cardNumber = request.getParameter("cardNumber");
                String expiry = request.getParameter("expiry");
                int cvv = Integer.parseInt(request.getParameter("cvv"));

                // Generate payment UUID
                String paymentUuid = UUID.randomUUID().toString();
                PaymentDetails paymentDetails = new PaymentDetails(paymentUuid, cardHolder, cardNumber, expiry, cvv);

                // Generate vehicle
                String vehicleUuid = UUID.randomUUID().toString();
                String carType = request.getParameter("carType");
                String regLocation = request.getParameter("regLocation");
                String regState = request.getParameter("regState");
                String licensePlate = request.getParameter("licensePlate");

                Vehicle vehicle = new Vehicle(vehicleUuid, carType, regLocation, regState, licensePlate);

                // Build Customer
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

                // Create User
                userDao.create(customer);

                // Set session attributes for authentication
                session.setAttribute("user", customer.getUserId());
                session.setAttribute("timeout", LocalDateTime.now().plusDays(7).toString());

                // Redirect to profile
                response.sendRedirect(request.getContextPath() + "/profile");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("/views/signup.jsp").forward(request, response);
        }
    }
}