package ub.chennegrin;

import ub.chennegrin.controllers.CatalegController;
import ub.chennegrin.controllers.LlibreriaController;
import ub.chennegrin.controllers.LoginController;
import ub.chennegrin.model.shop.ShopManager;
import ub.chennegrin.model.users.UsersManager;
import ub.chennegrin.utils.JSONLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ServletDispatcher extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void init() throws ServletException {
        try {
            InputStream productsStream = getServletContext().getResourceAsStream("WEB-INF/media/products.json");
            JSONLoader.loadProductsFromJson(productsStream, ShopManager.getInstance());

            InputStream usersStream = getServletContext().getResourceAsStream("WEB-INF/users/users.json");
            JSONLoader.loadUsersFromJson(usersStream, UsersManager.getInstance());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String endPoint = req.getRequestURI();
        switch (endPoint) {
            case "/":
                homeRequest(req, resp);
                break;
            case "/llibreria":
                llibreriaRequest(req, resp);
                break;
            case "/llibreria/cataleg":
                llibreriaCatalegRequest(req, resp);
                break;
            case "/login":
                loginRequest(req, resp);
                break;
        }
    }

    private void loginRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new LoginController().processRequest(this, req, resp);
    }

    private void llibreriaCatalegRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new CatalegController().processRequest(this, req, resp);
    }

    private void homeRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/llibreria");
    }

    private void llibreriaRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new LlibreriaController().processRequest(this, req, resp);
    }
}
