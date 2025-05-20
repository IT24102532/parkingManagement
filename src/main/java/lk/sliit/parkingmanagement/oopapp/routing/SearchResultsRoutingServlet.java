// This servlet is meant to handle the continuation of a search process
package lk.sliit.parkingmanagement.oopapp.routing;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "SearchResultsRoutingServlet", value = "/search/continue")
public class SearchResultsRoutingServlet extends HttpServlet {

    // Logger used to capture errors or important events (handy for debugging)
    private final Logger LOGGER = Logger.getLogger(SearchResultsRoutingServlet.class.getName());

    // This method will run when the user submits something via POST to /search/continue
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}