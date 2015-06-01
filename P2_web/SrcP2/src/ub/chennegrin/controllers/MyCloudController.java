package ub.chennegrin.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ub.chennegrin.ServletDispatcher;
import ub.chennegrin.model.shop.Product;
import ub.chennegrin.model.shop.ShopManager;
import ub.chennegrin.model.users.User;

/**
 *
 * @author Huang
 */
public class MyCloudController extends PageController {

    public MyCloudController() {
    }

    @Override
    public void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/protected/mycloud/mycloud.jsp").forward(req, resp);
    }

    @Override
    public void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public List<Product> getPurchasedProducts(HttpServletRequest req) {
        User user = (User) req.getAttribute("User");
        ShopManager manager = ShopManager.getInstance();
        ArrayList<Product> products = new ArrayList<>();
        for (int productId : user.getPurchased()) {
            Product p = manager.findProductById(productId);
            products.add(0, p);
        }
        return products;
    }

}