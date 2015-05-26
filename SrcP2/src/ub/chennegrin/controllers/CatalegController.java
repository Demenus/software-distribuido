package ub.chennegrin.controllers;

import ub.chennegrin.ServletDispatcher;
import ub.chennegrin.model.shop.Product;
import ub.chennegrin.model.shop.ShopManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by aaron on 21/05/2015.
 */
public class CatalegController extends PageController {
    @Override
    public void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setAttribute("Controller", this);
        req.getRequestDispatcher("/jsp/llibreria-cataleg.jsp").forward(req, resp);
    }

    @Override
    public void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public Collection<Product> getAllProducts() {
        return ShopManager.getInstance().getAllProducts();
    }
}
