package lk.sliit.parkingmanagement.oopapp.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TestServlet", value = "/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String test = request.getParameter("test");
// Simulate HTTP 400 error
        if ("400".equals(test)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test 400 Error");
            return;
            // Simulate bad request errors with custom messages
        } else if ("500".equals(test)) {
            throw new RuntimeException("Test 500 Error");
        } else if ("expectionNull".equals(test)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test expectionNull Error");
        } else if ("expectionEmpty".equals(test)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test expectionEmpty Error");
        } else if ("expectionInvalid".equals(test)) {
            // Forward to banned.jsp page if test = banned
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test expectionInvalid Error");
        } else if ("banned".equalsIgnoreCase(test)) {
            request.getRequestDispatcher("/WEB-INF/banned.jsp").forward(request, response);
        }
// If no matching case, send a normal response.
        response.getWriter().println("Normal Response");
    }
}