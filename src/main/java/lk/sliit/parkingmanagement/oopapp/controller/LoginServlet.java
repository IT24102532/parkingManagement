package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
  This servlet handles user login functionality for the parking management system

  Listen to both POST and GET requests
 */

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {
    // Private Initialization
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private final UserDao userDao = new UserDaoImpl();

    @Override
    //forward GET request to the login JSP
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get 'email' and 'password' from form input
        String email    = Optional.ofNullable(request.getParameter("email")).orElse("").trim();
        String password = Optional.ofNullable(request.getParameter("password")).orElse("").trim();

        // validate that both fields are filled
        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Please fill all the required fields");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        //retrieve user by email
        User user = userDao.findByEmail(email);
        try {
            if (user == null || !userDao.validatePasswordByEmail(email, password)) {
                // If user not found or password doesn't match gives an error
                request.setAttribute("error", "Email or password is incorrect");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            //log exception if failure
            Log.type(LogType.ERROR).message("Error: "+e.getMessage());
        }

        //create a new session for logged-in user
        HttpSession session = request.getSession(true);
        assert user != null;

        //check if user is banned
        if (user.getBanned()) {
            request.getRequestDispatcher("/WEB-INF/banned.jsp").forward(request, response);
            return;
        }

        session.setAttribute("user", user.getUserId());
        String userId = user.getUserId();
        System.out.println(user.getUserId());

        // redirect to previously requested page after login
        String redirect = (String) session.getAttribute("redirectAfterLogin");
        if (redirect != null) {
            session.removeAttribute("redirectAfterLogin");
            response.sendRedirect(request.getContextPath() + redirect);
        } else {
            //redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}