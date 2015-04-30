package ub.chennegrin;

import ub.chennegrin.controllers.LlibreriaController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDispatcher extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ctx = req.getContextPath();
        String endPoint = req.getRequestURI();
        switch (endPoint) {
            case "/":
                homeRequest(req, resp);
                break;
            case "/llibreria":
                llibreriaRequest(req, resp);
                break;
        }
    }

    private void homeRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/llibreria");
    }

    private void llibreriaRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new LlibreriaController().processRequest(getServletContext(), req, resp);
    }
}
