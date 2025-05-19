package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/*
    This servlet handles user logout functionality for the parking management system

    This only listen for GET request
 */

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action");

        // If the action is 'switch', log out the current user and redirect to login page
        if ("switch".equals(action)) {
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        //default logout
        if (session != null) {
            session.invalidate();
        }

        //redirect to home page
        response.sendRedirect(request.getContextPath() + "/");
    }
}