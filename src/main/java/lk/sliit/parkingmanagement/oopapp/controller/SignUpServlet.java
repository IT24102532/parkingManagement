package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.model.PaymentDetails;
import lk.sliit.parkingmanagement.oopapp.model.Vehicle;
import lk.sliit.parkingmanagement.oopapp.FilleHander.UserFileHandler;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {
    UserFileHandler handler = new UserFileHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String step = request.getParameter("step");

        if ("user".equals(step)) {
            // Step 1: User info
            session.setAttribute("firstName", request.getParameter("firstName"));
            session.setAttribute("lastName", request.getParameter("lastName"));
            session.setAttribute("email", request.getParameter("email"));
            String hashedPassword = PasswordHasher.hashPassword(request.getParameter("password"));
            session.setAttribute("hashedPassword", hashedPassword);

            request.getRequestDispatcher("/views/vehicleDetails.jsp").forward(request, response);

        } else if ("vehicle".equals(step)) {
            // Step 2: Vehicle info
            session.setAttribute("vehicleType", request.getParameter("vehicleType"));
            session.setAttribute("regCountry", request.getParameter("regCountry"));
            session.setAttribute("regState", request.getParameter("regState"));
            session.setAttribute("regNumber", request.getParameter("regNumber"));

            request.getRequestDispatcher("/views/paymentDetails.jsp").forward(request, response);

        } else if ("payment".equals(step)) {
            // Step 3: Payment info & final registration

            // Payment details
            String cardHolder = request.getParameter("cardHolder");
            String cardType = request.getParameter("cardType");
            String expiry = request.getParameter("expiry");
            int cardNumber = Integer.parseInt(request.getParameter("cardNumber")); // NOTE: You might want to store this as a String in real systems
            String cardId = UUID.randomUUID().toString();
            PaymentDetails payment = new PaymentDetails(cardId, cardHolder, expiry, cardType, cardNumber);

            // Vehicle object
            String vehicleId = UUID.randomUUID().toString();
            Vehicle vehicle = new Vehicle(
                    vehicleId,
                    (String) session.getAttribute("vehicleType"),
                    (String) session.getAttribute("regCountry"),
                    (String) session.getAttribute("regState"),
                    (String) session.getAttribute("regNumber")
            );

            // Customer object
            String userId = UUID.randomUUID().toString();
            Customer customer = new Customer(
                    userId,
                    (String) session.getAttribute("firstName"),
                    (String) session.getAttribute("lastName"),
                    (String) session.getAttribute("email"),
                    (String) session.getAttribute("hashedPassword"),
                    "customer",
                    new ArrayList<>(),
                    vehicle,
                    payment
            );

            // Save customer using file handler
            handler.saveCustomer(customer);
            // Clear session attributes (optional)
            session.invalidate();

            // Redirect to confirmation page
            request.setAttribute("message", "Sign up successful!");
            request.getRequestDispatcher("/views/signupSuccess.jsp").forward(request, response);
        }
    }
}
