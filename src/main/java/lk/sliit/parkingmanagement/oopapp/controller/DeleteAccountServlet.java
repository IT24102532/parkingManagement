package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.User;

import java.io.IOException;

@WebServlet(name = "DeleteAccountServlet", value = "/delete")
public class DeleteAccountServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String id = request.getParameter("id");
        if (password == null || id == null || password.isBlank() || id.isBlank()) {
            request.setAttribute("status", false);
            request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
            return;
        }
        try {
            User user = userDao.getById(id);
            if (user == null) {
                request.setAttribute("status", false);
                request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
                return;
            }
            boolean valid = userDao.validatePasswordById(id, password);
            if (valid) {
                userDao.delete(id);
                HttpSession session = request.getSession();
                if (session != null) {
                    session.invalidate();
                }
                request.setAttribute("status", true);
            } else {
                request.setAttribute("status", false);
            }
            request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", false);
            request.getRequestDispatcher("/views/accountdeleted.jsp").forward(request, response);
        }
    }
}