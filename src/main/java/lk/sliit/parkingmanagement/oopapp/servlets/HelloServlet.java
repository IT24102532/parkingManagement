package lk.sliit.parkingmanagement.oopapp.servlets;

import java.io.*;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.*;

@WebServlet(name = "signup", value = "/signup")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "USer Doesn't exist";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");

        if (email != null) {
            JsonHelper<User> userJsonHelper = new JsonHelper<>("data/users.json", User.class);
            User user = userJsonHelper.findOne(user1 -> user1.getEmail().equals(email));

            if (user != null) {
                request.setAttribute("user", user);
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
        String userType = request.getParameter("userType");
        String carType = request.getParameter("carType");
        String licensePlate = request.getParameter("license");
        String passwordHash = PasswordHasher.hashPassword(password);
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");

        JsonHelper<User> userHelper = new JsonHelper<User>("data/users.json", User.class);
        PaymentDetails card = new PaymentDetails(cardNumber, expiryDate);

        System.out.println("Absolute path: " + new File("data/users.json").getAbsolutePath());

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

        response.sendRedirect(request.getContextPath() + "/signup?email=" + email);
    }

    public void destroy() {}
}