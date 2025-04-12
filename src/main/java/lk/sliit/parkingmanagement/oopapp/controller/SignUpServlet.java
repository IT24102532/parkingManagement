package lk.sliit.parkingmanagement.oopapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/Signin")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO:
        // 1. Check if the user is already signed in, using the session cookies, if they are, simply redirect
        //    them to park.me/dash
        // 2. If they are not signed in previously, then forward them to park.me/join
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO:
        // 1. Store all the details and create a new User
        // 2. Forward them to park.me/dash
    }
}