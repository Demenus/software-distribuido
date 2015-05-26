package ub.chennegrin.controllers;

import ub.chennegrin.ServletDispatcher;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aaron on 24/05/2015.
 */
public class LoginController extends PageController {
    @Override
    public void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        resp.sendRedirect("/llibreria");
    }

    @Override
    public void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pwd = req.getParameter("password");

        resp.sendRedirect("/llibreria");
    }
}
