package lk.sliit.parkingmanagement.oopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.dao.*;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.model.Transaction;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "GetTransactionAmount", value = "/get/transaction/data")
public class GetTransactionAmount extends HttpServlet {
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");

        // TODO: validate admin user for production
        LocalDateTime startOfWeek = LocalDate.now()
                .with(DayOfWeek.MONDAY)
                .atStartOfDay();

        List<Transaction> transactions;
        try {
            transactions = transactionDao.findAll().stream()
                    .filter(t -> t.getCreatedAt() != null && t.getCreatedAt().isAfter(startOfWeek))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving transactions.");
            return;
        }

        Map<DayOfWeek, Double> groupedTotals = transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCreatedAt().getDayOfWeek(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        List<DayOfWeek> weekOrder = List.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
        );

        List<Map<String, Object>> transactionData = new ArrayList<>();
        for (DayOfWeek day : weekOrder) {
            double total = groupedTotals.getOrDefault(day, 0.0);
            Map<String, Object> entry = new HashMap<>();
            entry.put("date", day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)); // Mon, Tue, etc.
            entry.put("total", total);
            transactionData.add(entry);
        }

        double totalAmount = groupedTotals.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        Map<String, Object> earningsEntry = new HashMap<>();
        earningsEntry.put("earnings", totalAmount);
        transactionData.add(earningsEntry);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        response.getWriter().write(gson.toJson(transactionData));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }
}
