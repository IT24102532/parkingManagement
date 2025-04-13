package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.FilleHander.UserFileHandler;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;
import lk.sliit.parkingmanagement.oopapp.model.PaymentDetails;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "SignUpServlet", value = "/Signup")
public class SignUpServlet extends HttpServlet {
    UserFileHandler handler = new UserFileHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String step = request.getParameter("step");

        if ("user".equals(step)) {
            // Step 1: Save user details to session
            session.setAttribute("username", request.getParameter("username"));
            session.setAttribute("email", request.getParameter("email"));
            String hashedPassword = PasswordHasher.hashPassword(request.getParameter("password"));
            session.setAttribute("hashedPassword", hashedPassword);

            request.getRequestDispatcher("/views/login.jsp").forward(request, response);

        } else if ("vehicle".equals(step)) {
            // Step 2: Save vehicle details
            session.setAttribute("carType", request.getParameter("carType"));
            session.setAttribute("licensePlate", request.getParameter("licensePlate"));

            response.sendRedirect("/views/paymentDetails.jsp");

        } else if ("payment".equals(step)) {
            // Step 3: Save payment and register
            String cardHolder = request.getParameter("cardHolder");
            String cardNumber = request.getParameter("cardNumber");
            String exp = request.getParameter("expiry");
            String cvv = request.getParameter("cvv");

            PaymentDetails paymentDetails = new PaymentDetails(cardHolder, cardNumber, exp, cvv);

            // Collect all session attributes
            String username = (String) session.getAttribute("username");
            String email = (String) session.getAttribute("email");
            String hashedPassword = (String) session.getAttribute("hashedPassword");
            String carType = (String) session.getAttribute("carType");
            String licensePlate = (String) session.getAttribute("licensePlate");
            String userId = UUID.randomUUID().toString();

            Customer customer = new Customer(username, email, userId, hashedPassword, carType, licensePlate, paymentDetails);

            // Save to file
            handler.saveCustomer(customer);

            // Optional: set user in session to mark them as "logged in"
            session.setAttribute("user", customer);

            // Redirect to dashboard or success page
            response.sendRedirect("/views/dashboard.jsp");
        }
    }
}
