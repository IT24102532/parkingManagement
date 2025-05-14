package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GlobalRoutingServlet", value = {"/home" , "/contact", "/about", "/services"})
public class GlobalRoutingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String jspPage = switch (path) {
            case "/home" -> "index.jsp";
            case "/contact" -> "/views/contact.jsp";
            case "/about" -> "/views/about.jsp";
            case "/services" -> "/views/services.jsp";
            default -> "/WEB-INF/error-404.jsp";
        };

        request.getRequestDispatcher(jspPage).forward(request, response);

    }
}