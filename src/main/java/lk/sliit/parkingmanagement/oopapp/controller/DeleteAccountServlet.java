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
import java.lang.reflect.Type;

/*
  This servlet handles the deletion of the user account from the system

  Only listen for POST requests at the endpoint /delete.
 */

@WebServlet(name = "DeleteAccountServlet", value = "/delete")
public class DeleteAccountServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    @Override

    // disallow GET requests on this endpoint
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        Log.type(LogType.INFO).message("Get request not allowed");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve password and user ID from request parameters
        String password = request.getParameter("password");
        String id = request.getParameter("id");

        //validate inputs
        if (password == null || id == null || password.isBlank() || id.isBlank()) {
            request.setAttribute("status", false);
            request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
            return;
        }
        try {
            //retrieve user by ID
            User user = userDao.getById(id);

            //if user does not exist ,return failure
            if (user == null) {
                request.setAttribute("status", false);
                request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
                return;
            }
            boolean valid = userDao.validatePasswordById(id, password);
            if (valid) {
                //if password is correct ,delete the account
                userDao.delete(id);
                HttpSession session = request.getSession();
                if (session != null) {
                    session.invalidate();
                }
                request.setAttribute("status", true);
            } else {
                request.setAttribute("status", false);
            }
            //forward to confirmation JSP with status
            request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
        } catch (Exception e) {
            //handle unexpected errors
            e.printStackTrace();
            request.setAttribute("status", false);
            request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
        }
    }
}