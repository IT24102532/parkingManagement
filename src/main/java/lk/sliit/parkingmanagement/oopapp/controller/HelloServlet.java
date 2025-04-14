package lk.sliit.parkingmanagement.oopapp.controller;

import java.io.*;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Admin;
import lk.sliit.parkingmanagement.oopapp.model.Customer;
import lk.sliit.parkingmanagement.oopapp.model.PaymentDetails;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;

@WebServlet(name = "test", value = "/test1")
public class HelloServlet extends HttpServlet {
    private String message;
    private String userFilePath = FileConfig.INSTANCE.getUsersPath();

    public void init() {
        message = "User Doesn't exist";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        System.out.println("Searching for email: " + email);

        if (email != null) {
            JsonHelper<User> userJsonHelper = new JsonHelper<>(userFilePath, User.class);
            List<User> users = userJsonHelper.readAll();
            System.out.println("Total users in file: " + users.size());
            User user = userJsonHelper.findOne(user1 -> user1.getEmail().equalsIgnoreCase(email));
            System.out.println("User found: " + (user != null ? user.toString() : "null"));

            if (user != null) {
                System.out.println("Actual class of user: " + user.getClass().getSimpleName());
                if (user instanceof Customer) {
                    request.setAttribute("user", (Customer) user);  // Cast User to Customer
                    System.out.println("Forwarding a Customer object to JSP");
                } else {
                    request.setAttribute("user", user);
                    System.out.println("Forwarding a User object to JSP");
                }
                RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);
            }
            else {
                response.getWriter().println("User Not Found");
            }
        }
        else {
            response.getWriter().println(message);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String userType = request.getParameter("user_type");
        String carType = request.getParameter("carType");
        String licensePlate = request.getParameter("license");
        String passwordHash = PasswordHasher.hashPassword(password);
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");

        JsonHelper<User> userHelper = new JsonHelper<User>(userFilePath, User.class);
        PaymentDetails card = new PaymentDetails(cardNumber, expiryDate);

        System.out.println("Absolute path: " + new File(userFilePath).getAbsolutePath());

        List<User> users = userHelper.readAll();
        int userId = users.stream()
                .map(u -> {
                    try {
                        return Integer.parseInt(u.getUserId().substring(1));
                    } catch (NumberFormatException | NullPointerException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0) + 1;


        if (userHelper.findOne(user -> user.getEmail().equalsIgnoreCase(email)) != null) {
            System.out.println("User already exists");
        } else if (userType.equalsIgnoreCase("user")) {
            Customer newUser = new Customer(username, email, ("U" + userId), passwordHash, carType, licensePlate, card);
            userHelper.create(newUser);
        } else if (userType.equalsIgnoreCase("admin")) {
            Admin newAdmin = new Admin(username, email, ("A" + userId), passwordHash);
            userHelper.create(newAdmin);
        }
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        response.sendRedirect(request.getContextPath() + "/test?email=" + email);
    }

    public void destroy() {}
}