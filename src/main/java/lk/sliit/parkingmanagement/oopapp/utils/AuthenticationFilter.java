package lk.sliit.parkingmanagement.oopapp.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.User;

import java.io.IOException;

// Add filters to paths that needs to be validated
@WebFilter(urlPatterns = {
        "/profile",
        "/dashboard",
        "/admin/*",
        "/search",
        "/insta",
        "/logout",
        "/logs",
        "/update",
        "/delete"
})
public class AuthenticationFilter implements Filter {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        String requestedPath = request.getRequestURI();
        String requestedUri = request.getRequestURI();

        if (session == null || session.getAttribute("user") == null) {
            session = request.getSession(true);
            session.setAttribute("redirectAfterLogin", requestedUri);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userId = (String) session.getAttribute("user");
        if (requestedPath.contains("/admin")) {
            try {
               User user = userDao.getById(userId);
               if (!"admin".equals(user.getUserType())) {
                   // Redirect non-admins from admin-only pages
                   response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");                    return;
               }
           } catch (Exception e) {
              response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User lookup failed");
               return;
           }
        }
        chain.doFilter(request, response);
    }
}