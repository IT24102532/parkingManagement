//package lk.sliit.parkingmanagement.oopapp.servlets;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//import lk.sliit.parkingmanagement.oopapp.JsonHelper;
//import lk.sliit.parkingmanagement.oopapp.ParkingSlot;
//import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//@WebServlet(name = "TestServlet", value = "/book")
//public class TestServlet extends HttpServlet {
//    private final String filePath = FileConfig.INSTANCE.getSlotPath();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);
//        String location = request.getParameter("location");
//        String slotId = request.getParameter("slotId");
//
//        List<ParkingSlot> slots = slotJsonHelper.findAll("location", location);
//        ParkingSlot selectedSlot = slotJsonHelper.findOne(allsSlots -> allsSlots.getParkingSlotID().equalsIgnoreCase(slotId));
//
//        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
//        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
//
//        Map<String, String> locations = new LinkedHashMap<>();
//        for (ParkingSlot slot : slots) {
//            locations.putIfAbsent(slot.getLocation(), slot.getLocation());
//        }
//
//        request.setAttribute("locations", locations);
//        request.setAttribute("slots", slots);
//        request.getRequestDispatcher("/test.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}