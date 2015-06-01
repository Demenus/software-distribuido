package ub.chennegrin;

import com.google.gson.Gson;
import ub.chennegrin.controllers.ConsulteController;
import ub.chennegrin.model.shop.Product;
import ub.chennegrin.model.shop.ShopManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The second part of delivery.
 */
public class WebServiceDispatcher extends HttpServlet {

    public WebServiceDispatcher() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new ConsulteController().processRequest(null, req, resp);
    }
}
