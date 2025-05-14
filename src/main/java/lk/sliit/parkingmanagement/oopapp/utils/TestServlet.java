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

        if ("400".equals(test)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test 400 Error");
            return;
        } else if ("500".equals(test)) {
            throw new RuntimeException("Test 500 Error");
        } else if ("expectionNull".equals(test)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test expectionNull Error");
        } else if ("expectionEmpty".equals(test)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test expectionEmpty Error");
        } else if ("expectionInvalid".equals(test)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test expectionInvalid Error");
        }

        response.getWriter().println("Normal Response");
    }
}