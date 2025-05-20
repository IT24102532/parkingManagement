// This servlet handles routing for multiple common pages like Home, Contact, About, and Services
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

// Map this servlet to several URL patterns
@WebServlet(name = "GlobalRoutingServlet", value = {"/home" , "/contact", "/about", "/services"})
public class GlobalRoutingServlet extends HttpServlet {

    // Handle GET requests (e.g., when someone visits one of the URLs above)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get the specific path the user requested (like /home or /contact)
        String path = request.getServletPath();

        // Decide which JSP page to load based on the path
        String jspPage = switch (path) {
            case "/home" -> "index.jsp";
            case "/contact" -> "/views/contact.jsp";
            case "/about" -> "/views/about.jsp";
            case "/services" -> "/views/services.jsp";
            default -> "/WEB-INF/error-404.jsp";
        };

        // Forward the request to the selected JSP page
        request.getRequestDispatcher(jspPage).forward(request, response);

    }
}