package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SearchRoutingServlet", value = "/search")
public class SearchRoutingServlet extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(NewBookingServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("user");
        try {
            request.setAttribute("user", userId);
            request.getRequestDispatcher("/views/long_term_search.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("user");
        String type = request.getParameter("type");
        String action = request.getParameter("action");

        if ("continue".equalsIgnoreCase(action)) {
            try {
                request.setAttribute("user", userId);
                request.setAttribute("startDate", request.getParameter("startDate"));
                request.setAttribute("endDate", request.getParameter("endDate"));
                request.setAttribute("vehicle", request.getParameter("vehicle"));
                request.setAttribute("location", request.getParameter("location"));

                if ("long_term".equals(type)) {
                    request.getRequestDispatcher("/views/lt_search_results.jsp").forward(request, response);
                } else if ("insta".equals(type)) {
                    request.getRequestDispatcher("/views/insta_results.jsp").forward(request, response);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Cannot retrieve user", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
            }
        }
        else if ("insta".equalsIgnoreCase(action)) {
            request.setAttribute("user", userId);
            request.getRequestDispatcher("/views/insta.jsp").forward(request, response);
        }
    }
}