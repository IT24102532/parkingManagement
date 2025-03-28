package lk.sliit.parkingmanagement.oopapp.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.Customer;
import lk.sliit.parkingmanagement.oopapp.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.PasswordHasher;
import lk.sliit.parkingmanagement.oopapp.User;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {
    private final String userFilePath = FileConfig.INSTANCE.getUsersPath();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            String profilePath = response.encodeRedirectURL(request.getContextPath() + "/profile");
            response.sendRedirect(profilePath);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DOGET Called");
        JsonHelper<User> userJsonHelper = new JsonHelper<User>(userFilePath, User.class);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Calling users");
        User user = userJsonHelper.findOne(user1 -> user1.getEmail().equalsIgnoreCase(email));

        if (user == null) {
            System.out.println("No user found");
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        System.out.println("Calling password hasher");
        boolean pwMatch = false;
        try {
             pwMatch = PasswordHasher.verifyPassword(password, user.getHashedPassword());
            System.out.println("Password Hasher Called");
        }
        catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            System.out.println("Error at password hasher");
            request.setAttribute("error", "Internal server error. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (pwMatch) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            String profilePath = response.encodeRedirectURL(request.getContextPath() + "/profile");
            response.sendRedirect(profilePath);
        }
        else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}