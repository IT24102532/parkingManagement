package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SearchResultsRoutingServlet", value = "/search/continue")
public class SearchResultsRoutingServlet extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(SearchResultsRoutingServlet.class.getName());
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}