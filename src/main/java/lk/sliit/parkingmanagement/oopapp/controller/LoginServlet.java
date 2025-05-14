package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.User;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {
    // Private Initialization
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private final UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email    = Optional.ofNullable(request.getParameter("email")).orElse("").trim();
        String password = Optional.ofNullable(request.getParameter("password")).orElse("").trim();

        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Please fill all the required fields");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        User user = userDao.findByEmail(email);
        try {
            if (user == null || !userDao.validatePasswordByEmail(email, password)) {
                request.setAttribute("error", "Email or password is incorrect");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        HttpSession session = request.getSession(true);
        assert user != null;
        session.setAttribute("user", user.getUserId());
        String userId = user.getUserId();
        System.out.println(user.getUserId());
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}