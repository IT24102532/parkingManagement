package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdminRoutingServlet", value = {
        "/admin/dashboard",
        "/admin/users",
        "/admin/transactions",
        "/admin/bookings",
        "/admin/slots",
        "/admin/profile"
})
public class AdminRoutingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String page  = switch (path) {
            case "/admin/dashboard" -> "/views/admin-dashboard.jsp";
            case "/admin/users" -> "/views/admin-users.jsp";
            case "/admin/transactions" -> "/views/admin-transactions.jsp";
            case "/admin/logs" -> "/views/admin-bookings.jsp";
            case "/admin/slots" -> "/views/admin-slots.jsp";
            case "/admin/profile" -> "/views/admin-profile.jsp";
            default -> "/WEB-INF/error-404.jsp";
        };
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}