package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import lk.sliit.parkingmanagement.oopapp.model.User;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "GetTransactionSearchServlet", value = {"/get/transactions/all","/get/transactions/search", "/get/transactions/count"})
public class GetTransactionSearchServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    private final Logger LOGGER = Logger.getLogger(GetTransactionSearchServlet.class.getName());

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd @HH:mm");

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> result;

        try {
            result = switch (request.getServletPath()) {
                case "/get/transactions/all" -> handleAllTransactions(request);
                case "/get/transactions/count" -> getCount(request);
                case "/get/transactions/search" -> handleUserSearch(request);
                default -> Map.of("status", "error", "message", "Invalid endpoint");
            };
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
        List<Transaction> transactions = transactionDao.findAll();

        double newTransactionsToday = 0;
        double newTransactionsMonth = 0;
        double totalTransactions = 0;
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();

        for (Transaction transaction : transactions) {
            LocalDate createdDate = transaction.getCreatedAt().toLocalDate();
            double amount = transaction.getAmount();

            totalTransactions += amount;

            if (createdDate.equals(today)) {
                newTransactionsToday += amount;
            }

            if (YearMonth.from(createdDate).equals(currentMonth)) {
                newTransactionsMonth += amount;
            }
        }

        result.put("status", "success");
        result.put("totalTransactions", totalTransactions);
        result.put("newTransactionsToday", newTransactionsToday);
        result.put("newTransactionsMonth", newTransactionsMonth);
        return result;
    }

    private Map<String, Object> handleUserSearch(HttpServletRequest request) throws Exception {
        String param = request.getParameter("param");
        Map<String, Object> result = new HashMap<>();

        if (param == null || param.trim().isEmpty()) {
            result.put("error", "Missing or empty search parameter");
            return result;
        }

        List<User> users = userDao.findAll().stream()
                .filter(u -> (u.getFirstName() + " " + u.getLastName())
                        .toLowerCase()
                        .contains(param.toLowerCase()))
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            result.put("transactions", Collections.emptyList());
            return result;
        }

        List<Map<String, Object>> allTransactions = new ArrayList<>();

        for (User user : users) {
            List<Transaction> transactions = transactionDao.findAll().stream()
                    .filter(t -> t.getUserId().equalsIgnoreCase(user.getUserId()))
                    .collect(Collectors.toList());
            for (Transaction transaction : transactions) {
                try {
                    allTransactions.add(mapTransaction(transaction));
                } catch (Exception e) {
                    LOGGER.warning("Skipping transaction due to mapping error: " + e.getMessage());
                }
            }
        }

        allTransactions.sort((a, b) -> {
            String dateA = (String) a.get("date");
            String dateB = (String) b.get("date");
            return dateB.compareTo(dateA);
        });

        result.put("transactions", allTransactions);
        return result;
    }


    private Map<String, Object> handleAllTransactions(HttpServletRequest request) throws Exception {
        List<Transaction> transactions = transactionDao.findAll();

        // Sort by created date (newest first)
        transactions.sort(Comparator.comparing(Transaction::getCreatedAt).reversed());

        String limitParam = request.getParameter("limit");
        if (limitParam != null) {
            try {
                int limit = Integer.parseInt(limitParam);
                if (limit > 0 && limit < transactions.size()) {
                    transactions = transactions.subList(0, limit);
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid limit parameter: " + limitParam + " from " + request.getRemoteAddr());
            }
        }

        List<Map<String, Object>> mappedTransactions = transactions.stream()
                .map(this::safeMapTransaction)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return Map.of("transactions", mappedTransactions);
    }

    private Map<String, Object> safeMapTransaction(Transaction transaction) {
        try {
            return mapTransaction(transaction);
        } catch (Exception e) {
            LOGGER.warning("Failed to map transaction ID " + transaction.getTransactionId() + ": " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> mapTransaction(Transaction transaction) throws Exception {
        User user = userDao.findById(transaction.getUserId());
        if (user == null) throw new Exception("User not found for ID " + transaction.getUserId());

        Booking booking = bookingDao.getById(transaction.getBookingId());
        if (booking == null) throw new Exception("Booking not found for ID " + transaction.getBookingId());

        ParkingSlot slot = parkingSlotDao.getById(booking.getSlotId());
        if (slot == null) throw new Exception("Slot not found for ID " + booking.getSlotId());

        Map<String, Object> entry = new HashMap<>();
        entry.put("id", transaction.getTransactionId());
        entry.put("date", formatDateTime(transaction.getCreatedAt()));
        entry.put("username", user.getFirstName() + " " + user.getLastName());
        entry.put("location", slot.getLocationName());
        entry.put("slot", slot.getSlotName());
        entry.put("amount", transaction.getAmount());
        return entry;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }
}
