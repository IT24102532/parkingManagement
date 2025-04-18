package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.FilleHander.UserFileHandler;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;
import lk.sliit.parkingmanagement.oopapp.model.PaymentDetails;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {
    UserDaoImpl userDao = new UserDaoImpl()
;
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

            request.getRequestDispatcher("/views/vehicleDetails.jsp").forward(request, response);

        } else if ("vehicle".equals(step)) {
            // Step 2: Save vehicle details
            session.setAttribute("carType", request.getParameter("carType"));
            session.setAttribute("licensePlate", request.getParameter("licensePlate"));

            request.getRequestDispatcher("/views/paymentDetails.jsp").forward(request, response);

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

            //save using DAO
            try{
                userDao.create(customer);
            }catch (Exception e){
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to save customer");
                    return;
            }


            // Optional: set user in session to mark them as "logged in"
            session.setAttribute("user", customer);

            // Redirect to dashboard or success page
            request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);


        }
    }
}
