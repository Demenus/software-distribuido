package ub.chennegrin.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LlibreriaController extends PageController {

    @Override
    public void doGet(ServletContext context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("Controller", this);
        req.getRequestDispatcher("/jsp/llibreria.jsp").forward(req, resp);
    }

    @Override
    public void doPost(ServletContext context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public String prueba() {
        return "Hola amigo mio!";
    }
}
