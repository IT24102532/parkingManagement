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


/*
    This servlet handles the process of updating a user's account information
    (first name and last name) in the parking management system.

    Only listen for POST request
 */


@WebServlet(name = "UpdateAccountServlet", value = "/update")
public class UpdateAccountServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        Log.type(LogType.INFO).message("Get request not allowed");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Recieved a request here");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String password = request.getParameter("password");
        String id = request.getParameter("id");

        System.out.println(fname + " " + lname + " " + password + " " + id);

        if (fname == null || lname == null || password == null || id == null ||
                fname.isBlank() || lname.isBlank() || password.isBlank() || id.isBlank()) {
            request.setAttribute("status", false);
            request.getRequestDispatcher("/views/accountupdated.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDao.getById(id);
            if (user == null) {
                request.setAttribute("status", false);
                request.getRequestDispatcher("/views/accountupdated.jsp").forward(request, response);
                return;
            }

            boolean valid = userDao.validatePasswordById(id, password);
            if (valid) {
                userDao.updateAccountDetails(fname, lname, id);
                request.setAttribute("status", true);
            } else {
                request.setAttribute("status", false);
            }
            request.getRequestDispatcher("/views/accountupdated.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", false);
            request.getRequestDispatcher("/views/accountupdated.jsp").forward(request, response);
        }
    }
}