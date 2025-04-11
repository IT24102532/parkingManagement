package lk.sliit.parkingmanagement.oopapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.sliit.parkingmanagement.oopapp.User;

public class PaymentDetailsServlet extends HttpServlet {

    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        //storing  card details
        HttpSession session = request.getSession(false);
        if ( session != null){
            User currentUser = (User) session.getAttribute("user");
            if( currentUser != null){
                String username = currentUser.getUsername();

            }
        }
        String cardNumber = request.getParameter("cardNumber");
        String  expiryDate = request.getParameter("expiryDate");
        String  cardNickName = request.getParameter("cardNickName");




    }

}
