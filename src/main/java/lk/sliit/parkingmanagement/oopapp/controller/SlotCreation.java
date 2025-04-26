package lk.sliit.parkingmanagement.oopapp.controller;


import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sliit.parkingmanagement.oopapp.dao.ParkingSlotDaoImpl;
import lk.sliit.parkingmanagement.oopapp.model.InstaSlot;
import lk.sliit.parkingmanagement.oopapp.model.LongTermSlot;
import lk.sliit.parkingmanagement.oopapp.model.Manager;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/slot-creation")
public class SlotCreation  extends HttpServlet {
    private  final ParkingSlotDaoImpl slotDao= new ParkingSlotDaoImpl();

    protected  void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

    // get parameters from the front end
    String location = request.getParameter("location");
    String  locationName=  request.getParameter("locationName");
    String priceParam = request.getParameter("price");
    String lotType = request.getParameter("lot-type");

            ;
    String managerID = UUID.randomUUID().toString();

    int maxDurationHours=12;


    // handling the parsing
        try {
            double price = Double.parseDouble(priceParam);
                // generate the slot id
                String  slotId = UUID.randomUUID().toString();

                // creating the manger object


            Manager manager =new Manager( UUID.randomUUID().toString(),12.0);
            // creting the slot object
            ParkingSlot slot;
            boolean isAvailable = true;
            if("longterm".equalsIgnoreCase(lotType)){
                slot = new LongTermSlot(
                        slotId,
                        location,
                        manager.getManagerId(),
                        "long-term",
                        locationName,
                        isAvailable,
                        price,
                        price * 0.15 // example overstay charge = 15% of price
                );
            }else if ("instaslot".equalsIgnoreCase(lotType)){
                slot = new InstaSlot(
                            slotId,
                        location,
                        managerID,
                        "insta",
                        locationName,
                        true,
                        price,
                        maxDurationHours,
                        price* 0.15



                );
            }else { 
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("invalid lot type");
                return;
            }
            // save to storage
            slotDao.create( slot);
            System.out.println("success fully created the slot");

            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Slot created successfully\"}");





        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


        // redirecting to the same servlet

        response.sendRedirect(request.getContextPath() + "/slot-creation");






    }

    protected  void doGet( HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException{

        request.setAttribute("message", "successfully added slot ");

        System.out.println("get method executed");
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);






    }


}
