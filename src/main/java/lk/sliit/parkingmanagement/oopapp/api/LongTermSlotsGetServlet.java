package lk.sliit.parkingmanagement.oopapp.api;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LongTermSlotsGetServlet", value = "/LongTermSlotsGetServlet")
public class LongTermSlotsGetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}