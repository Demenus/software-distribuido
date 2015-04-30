package ub.chennegrin.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class PageController {

    public void processRequest(ServletContext context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("GET")) {
            doGet(context, req, resp);
        } else if (req.getMethod().equalsIgnoreCase("POST")) {
            doPost(context, req, resp);
        }
    }

    public abstract void doGet(ServletContext context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public abstract void doPost(ServletContext context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
