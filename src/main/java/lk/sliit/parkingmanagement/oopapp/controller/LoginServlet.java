package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {
    // Private Initialization
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        HttpSession session = request.getSession(false);
        boolean isValid = false;

        if (session != null) {
            Object user = session.getAttribute("user");
            Object timeout = session.getAttribute("timeout");

            if (user != null && timeout != null) {
                try {
                    LocalDateTime currentTimeout = LocalDateTime.parse(timeout.toString());
                    LocalDateTime now = LocalDateTime.now();

                    if (currentTimeout.isBefore(now)) {isValid = true;}
                }
                catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error parsing session timeout", e);
                }
            }
            if (isValid) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/profile"));
            }
            else {
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDaoImpl();
        // get the attributes
        String email = Optional.ofNullable(request.getParameter("email")).orElse("").trim();
        String password = Optional.ofNullable(request.getParameter("password")).orElse("").trim();

        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Please fill all the required fields");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        Optional<User> userOpt = Optional.ofNullable(userDao.findByEmail(email));
        // Details validation
        if (userOpt.isEmpty()) {
            request.setAttribute("error", "Email or password is incorrect");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }
        // locate the user attempting to sign in
        User user = userOpt.get();
        boolean isPasswordValid = false;
        try {
            // validate stored password
            isPasswordValid = userDao.validatePassword(email, password);
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
        // Invalidate the current session and create a new one
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        session = request.getSession(true);
        LocalDateTime timeout = LocalDateTime.now().plusDays(7);

        // Store only the user_uuid instead of user object.
        session.setAttribute("user", user.getUserId());
        session.setAttribute("timeout", timeout);
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/profile"));
    }
}