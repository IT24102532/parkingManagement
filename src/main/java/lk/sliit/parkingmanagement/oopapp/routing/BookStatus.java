package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "BookStatus", value = "/book/status")
public class BookStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean status = (Boolean) request.getSession().getAttribute("bookingStatus");
        request.setAttribute("status", status);
        request.getRequestDispatcher("/views/bookingstatus.jsp").forward(request, response);
        request.getSession(false).removeAttribute("bookingStatus");
    }
}