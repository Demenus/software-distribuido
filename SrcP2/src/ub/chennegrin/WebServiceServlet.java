package ub.chennegrin;


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

public class WebServiceServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void init() throws ServletException {
        try {
            InputStream productsStream = getServletContext().getResourceAsStream("WEB-INF/media/products.json");
            ShopManager shopManager = ShopManager.getInstance();
            JSONLoader.loadProductsFromJson(productsStream, shopManager);
            shopManager.initialize();

            InputStream usersStream = getServletContext().getResourceAsStream("WEB-INF/users/users.json");
            JSONLoader.loadUsersFromJson(usersStream, UsersManager.getInstance());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* resp.setContentType("application/json");
         resp.setHeader("Access-Control-Allow-Origin","*");*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

    }




}
