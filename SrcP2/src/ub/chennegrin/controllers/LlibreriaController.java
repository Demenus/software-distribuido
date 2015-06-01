package ub.chennegrin.controllers;

import ub.chennegrin.ServletDispatcher;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LlibreriaController extends PageController {

    @Override
    public void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setAttribute("Controller", this);
        req.getRequestDispatcher("/WEB-INF/jsp/llibreria/llibreria.jsp").forward(req, resp);
    }

    @Override
    public void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
