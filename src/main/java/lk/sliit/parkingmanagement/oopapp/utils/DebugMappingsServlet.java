package lk.sliit.parkingmanagement.oopapp.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

@WebServlet("/debug/mappings")
public class DebugMappingsServlet extends HttpServlet {
    /**
     * Handles GET requests to display component mappings
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    // Set response type to plain text

        response.setContentType("text/plain");
        ServletContext context = getServletContext();

        // Section 1: Display registered servlets
        response.getWriter().println("=== SERVLETS ===");
        Map<String, ? extends ServletRegistration> servlets = context.getServletRegistrations();
        for (Map.Entry<String, ? extends ServletRegistration> entry : servlets.entrySet()) {
            ServletRegistration reg = entry.getValue();
            response.getWriter().println("Servlet Name: " + reg.getName());
            response.getWriter().println("Class: " + reg.getClassName());
            for (String mapping : reg.getMappings()) {
                response.getWriter().println("  Mapping: " + mapping);
            }
            response.getWriter().println();
        }
        // Section 2: Display registered filters and their associations
        response.getWriter().println("=== FILTERS ===");
        Map<String, ? extends FilterRegistration> filters = context.getFilterRegistrations();
        for (Map.Entry<String, ? extends FilterRegistration> entry : filters.entrySet()) {
            FilterRegistration reg = entry.getValue();
            response.getWriter().println("Filter Name: " + reg.getName());
            response.getWriter().println("Class: " + reg.getClassName());
            for (String mapping : reg.getServletNameMappings()) {
                response.getWriter().println("  Mapping: " + mapping);
            }
            response.getWriter().println();
        }

        response.getWriter().println("=== LISTENERS ===");
        try {
            Field listenersField = context.getClass().getDeclaredField("eventListenersList");
            listenersField.setAccessible(true);
            @SuppressWarnings("unchecked")
            // List all registered event listeners
            List<EventListener> listeners = (List<EventListener>) listenersField.get(context);
            for (EventListener listener : listeners) {
                response.getWriter().println("Listener: " + listener.getClass().getName());
            }
        } catch (Exception e) {
            response.getWriter().println("Could not introspect listeners: " + e.getMessage());
        }
    }
}
