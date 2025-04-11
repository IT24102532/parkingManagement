package lk.sliit.parkingmanagement.oopapp.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.sliit.parkingmanagement.oopapp.FilleHander.UserFileHandler;
import lk.sliit.parkingmanagement.oopapp.User;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "SignUpServlet", value = "/Signup")
public class SignUpServlet extends HttpServlet {
    UserFileHandler handler  = new UserFileHandler();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO:
        // 1. Check if the user is already signed in, using the session cookies, if they are, simply redirect
        //    them to park.me/dash
        // 2. If they are not signed in previously, then forward them to park.me/join

        HttpSession session = request.getSession(false)
;
        //check if  the user is already signed in
        if( session != null && session.getAttribute("user") != null){
            // user is already logged in , redirect to dashboard
            response.sendRedirect("dash");
        }else{
            // user not logged in    forward to sign up page
            RequestDispatcher dispatcher= request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO:
        // 1. Store all the details and create a new User
        // 2. Forward them to park.me/dash
        String   username = request.getParameter("username");
        String email = request.getParameter("email");
        String   password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String   userType = request.getParameter("userType");



        // validate the user
        if( username  == null || email == null || password == null || userType == null ||username.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            request.setAttribute("error","All fields are required");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }
        if(!password.equals(confirmPassword)){
            request.setAttribute("error","Password do not match ");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return ;
        }


        //create User object
        User    newUser= new User();
        newUser.setUserId(UUID.randomUUID().toString());
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setHashedPassword(password);
        newUser.setUserType(userType);

        //save to file
        handler.addUserToFile(newUser);
        // store the user  in session
        HttpSession session = request.getSession();
        session.setAttribute("user", newUser);

        // redirect to the dashboard
        response.sendRedirect(request.getContextPath() + "/cardDetails.jsp");








    }
}