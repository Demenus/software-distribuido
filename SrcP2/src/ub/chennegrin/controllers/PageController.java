package ub.chennegrin.controllers;

import ub.chennegrin.ServletDispatcher;
import ub.chennegrin.model.shop.CartList;
import ub.chennegrin.model.users.User;
import ub.chennegrin.model.users.UsersManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class PageController {

    public void processRequest(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("Controller", this);
        setUser(req);
        if (req.getMethod().equalsIgnoreCase("GET")) {
            doGet(context, req, resp);
        } else if (req.getMethod().equalsIgnoreCase("POST")) {
            doPost(context, req, resp);
        }
    }

    private void setUser(HttpServletRequest req) {
        String username = req.getRemoteUser();
        if (req.getAttribute("User") == null) {
            User user = UsersManager.getInstance().findUserByName(username);
            req.setAttribute("User", user);
            if (user != null && req.getSession().getAttribute("CartList") == null) {
                CartList cartList = new CartList();
                req.getSession().setAttribute("CartList", cartList);
                cartList.assignUser(user);
                user.addCartList(req.getSession().getId(), cartList);
            }
        }
    }

    public abstract void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public abstract void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
