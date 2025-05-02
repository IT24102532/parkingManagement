package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.utils.PasswordHasher;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "BookingServlet", value = "/booking")
public class BookingServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BookingServlet.class.getName());
    private final String userFilePath = FileConfig.INSTANCE.getUsersPath();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/booking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonHelper<User> userJsonHelper = new JsonHelper<>(userFilePath, User.class);

        String email = Optional.ofNullable(request.getParameter("email")).orElse("").trim();
        String password = Optional.ofNullable(request.getParameter("password")).orElse("").trim();

        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Please fill all the required fields");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        Optional<User> userOpt = Optional.ofNullable(userJsonHelper.findOne(user1 -> user1.getEmail().equalsIgnoreCase(email)));

        if (userOpt.isEmpty()) {
            request.setAttribute("error", "Email or password is incorrect");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        User user = userOpt.get();
        boolean isPasswordValid = false;
        try {
            isPasswordValid = PasswordHasher.verifyPassword(password, user.getHashedPassword());
            System.out.println("Password Hasher Called");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Password Hasher Failed", e);
            request.setAttribute("error", "Internal server error. Please try again.");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        if (!isPasswordValid) {
            request.setAttribute("error", "Incorrect password");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        session = request.getSession(true);
        session.setAttribute("user", user);
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/profile"));
    }
}
