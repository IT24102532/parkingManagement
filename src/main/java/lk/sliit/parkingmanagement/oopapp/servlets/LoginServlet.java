package lk.sliit.parkingmanagement.oopapp.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.JsonHelper;
import lk.sliit.parkingmanagement.oopapp.PasswordHasher;
import lk.sliit.parkingmanagement.oopapp.User;

import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("dashboard.jsp");
        }
        else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonHelper<User> userJsonHelper = new JsonHelper<User>("/WEB-INF/data/users.json", User.class);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userJsonHelper.findOne(user1 -> user1.getEmail().equalsIgnoreCase(email));

        if (user == null) {
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        boolean pwMatch = PasswordHasher.verifyPassword(password, user.getHashedPassword());

        if (pwMatch) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("dashboard.jsp");
        }
        else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}