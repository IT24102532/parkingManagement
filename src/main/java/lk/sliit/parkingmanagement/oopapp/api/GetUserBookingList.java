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
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalDateTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.LocalTimeAdapter;
import lk.sliit.parkingmanagement.oopapp.utils.Log.Log;
import lk.sliit.parkingmanagement.oopapp.utils.Log.LogType;
import lk.sliit.parkingmanagement.oopapp.utils.Sort.GlobalSort;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
/*
*Servlet designed to retrieve a list of all bookings made by a specific user
*
* param id - fetch the user's transaction and booking history
*
* Reads data from transaction , booking and parkingSlots
* filters transactions by userid and sorts them by global utility
* and collect the corresponding booking details
*
* the data returned as a JSON response for frontend use
* such as displaying booking history
*
*/

@WebServlet(name = "GetUserBookingList", value = "/get/booking/list")
public class GetUserBookingList extends HttpServlet {
    //DAO Access
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //LOG the entry point
        Log.type(LogType.INFO).message("/get/booking/list").print();
        String userId = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            //fetch all transactions belongs to the specified user
            List<Transaction> userTransactions = transactionDao.findAll().stream()
                    .filter(t -> t.getUserId().equals(userId))
                    .collect(Collectors.toList());
            GlobalSort.insertionSortByDates(userTransactions);
            Set<String> bookingIds = userTransactions.stream()
                    .map(Transaction::getBookingId)
                    .collect(Collectors.toSet());
            //retrieve bookings that match those booking IDs
            List<Booking> userBookings = bookingDao.findAll().stream()
                    .filter(b -> bookingIds.contains(b.getBookingId()))
                    .collect(Collectors.toList());
            List<Map<String, Object>> joinedData = new ArrayList<>();
            //for each transaction find its matching booking
            for (Transaction transaction : userTransactions) {
                userBookings.stream()
                        .filter(booking -> booking.getBookingId().equals(transaction.getBookingId()))
                        .findFirst()
                        .ifPresent(booking -> {
                            Map<String, Object> entry = new LinkedHashMap<>();
                            entry.put("transactionId", transaction.getTransactionId());
                            entry.put("amount", transaction.getAmount());
                            //add the date of the transaction
                            LocalDate date = transaction.getCreatedAt().toLocalDate();
                            entry.put("date", date);
                            try {
                                //fetching slot details
                                ParkingSlot slot = parkingSlotDao.getById(booking.getSlotId());
                                entry.put("location", slot.getLocationName());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            joinedData.add(entry);
                        });
            }
            Log.type(LogType.NOTE).message("@/get/booking/list -> finalized data").print();
            response.getWriter().write(gson.toJson(joinedData));
            //log success
            Log.type(LogType.INFO).message("[/get/booking/list] successfully sent data").print();
        } catch (Exception e) {
            //catch the error 
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch bookings.");
            Log.type(LogType.ERROR).message("[/get/booking/list] failed to deliver data").print();
        }
    }

//    private void insertionSortByCreatedAt(List<Transaction> list) {
//        for (int i = 1; i < list.size(); i++) {
//            Transaction key = list.get(i);
//            LocalDateTime kt = key.getCreatedAt();
//            int j = i - 1;
//            while (j >= 0 && list.get(j).getCreatedAt().isBefore(kt)) {
//                list.set(j + 1, list.get(j));
//                j--;
//            }
//            list.set(j + 1, key);
//        }
//    }
}