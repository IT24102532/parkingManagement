package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "BookStatus", value = "/book/status")
public class BookStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the booking status from the session (perhaps null if not set)
        Boolean status = (Boolean) request.getSession().getAttribute("bookingStatus");

        // Set the retrieved status as a request attribute to be accessible in the JSP
        request.setAttribute("status", status);

        // Forward the request to the booking status view (JSP page)
        request.getRequestDispatcher("/views/bookingstatus.jsp").forward(request, response);

        // Remove the bookingStatus attribute from the session to prevent reuse
        request.getSession(false).removeAttribute("bookingStatus");
    }
}