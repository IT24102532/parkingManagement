package lk.sliit.parkingmanagement.oopapp.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Add filters to paths that needs to be validated
@WebFilter(urlPatterns = { "/profile", "/dashboard", "/admin/*" })
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (!loggedIn) {
            // Not logged in
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
