package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CheckoutRoutingServlet", value = "/checkout")
public class CheckoutRoutingServlet extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(CheckoutRoutingServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("user");
        session.setAttribute("slotId", request.getParameter("slotId"));
        session.setAttribute("startDate", request.getParameter("startDate"));
        session.setAttribute("endDate", request.getParameter("endDate"));
        System.out.println(session.getAttribute("slotId"));
        System.out.println(session.getAttribute("startDate"));
        System.out.println(session.getAttribute("endDate"));
        System.out.println(session.getAttribute("user"));
        try {
            request.setAttribute("user", userId);
            request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}