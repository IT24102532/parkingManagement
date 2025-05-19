package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.UserDao;
import lk.sliit.parkingmanagement.oopapp.dao.UserDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "GetUserSearchServlet", value = {"/get/user/search", "/get/user/all", "/get/user/count"})
public class GetUserSearchServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final Logger LOGGER = Logger.getLogger(GetUserSearchServlet.class.getName());

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> result;

        try {
            switch (request.getServletPath()) {
                case "/get/user/search":
                    result = handleUserSearch(request);
                    break;
                case "/get/user/all":
                    result = handleAllUsers(request);
                    break;
                case "/get/user/count":
                    result = getCount(request);
                    break;
                default:
                    result = Map.of("status", "error", "message", "Invalid endpoint");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            result = Map.of("status", "error", "message", "Internal server error");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }

    private Map<String, Object> getCount(HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userDao.findAll();

        int newUsersToday = 0;
        int newUsersMonth = 0;
        int totalUsers = users.size();
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();

        for (User user : users) {
            LocalDate createdDate = user.getCreatedAt().toLocalDate();

            if (createdDate.equals(today)) {
                newUsersToday++;
            }

            if (YearMonth.from(createdDate).equals(currentMonth)) {
                newUsersMonth++;
            }
        }
        result.put("status", "success");
        result.put("totalUsers", totalUsers);
        result.put("newUsersToday", newUsersToday);
        result.put("newUsersMonth", newUsersMonth);
        return result;
    }


    private Map<String, Object> handleUserSearch(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        String param = request.getParameter("param");
        List<User> users = userDao.findAll();
        Map<String, Object> result = new HashMap<>();

        if (id != null) {
            User matchedUser = users.stream()
                    .filter(u -> u.getUserId().equalsIgnoreCase(id))
                    .findFirst()
                    .orElse(null);

            if (matchedUser != null) {
                result.put("user", mapUser(matchedUser));
            } else {
                result.put("error", "User not found");
            }

        } else if (param != null) {
            List<Map<String, Object>> matchedUsers = users.stream()
                    .filter(u -> u.getUsername().toLowerCase().contains(param.toLowerCase()) ||
                            u.getEmail().toLowerCase().contains(param.toLowerCase()))
                    .map(this::mapUser)
                    .collect(Collectors.toList());

            result.put("users", matchedUsers);

        } else {
            result.put("error", "Missing search parameter");
        }

        return result;
    }

    private Map<String, Object> handleAllUsers(HttpServletRequest request) throws Exception {
        List<User> users = userDao.findAll();
        String limitParam = request.getParameter("limit");

        List<User> limitedUsers = users;
        if (limitParam != null) {
            try {
                int limit = Integer.parseInt(limitParam);
                limitedUsers = users.stream()
                        .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                        .limit(limit)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid limit parameter: " + limitParam);
            }
        }

        List<Map<String, Object>> mappedUsers = limitedUsers.stream()
                .map(this::mapUser)
                .collect(Collectors.toList());

        return Map.of("users", mappedUsers);
    }


    private Map<String, Object> mapUser(User user) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("name", user.getFirstName() + " " + user.getLastName());
        entry.put("id", user.getUserId());
        entry.put("joined", formatDateTime(user.getCreatedAt()));
        entry.put("email", user.getEmail());
        entry.put("location", "Sri Lanka");
        entry.put("status", user.getBanned() ? "Banned" : "Active");
        entry.put("fname", user.getFirstName());
        entry.put("lname", user.getLastName());
        return entry;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd @HH:mm"));
    }
}
