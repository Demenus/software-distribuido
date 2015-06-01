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
import java.util.ArrayList;

/**
 * Created by aaron on 24/05/2015.
 */
public class LoginController extends PageController {

    @Override
    protected void setUser(HttpServletRequest req) {
        String username = req.getRemoteUser();
        UsersManager manager = UsersManager.getInstance();
        if (manager.findUserByName(username) == null) {
            manager.createNewUser(username, UsersManager.DEFAULT_USER_CURRENCY);
        }
        super.setUser(req);
    }

    @Override
    public void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect("/llibreria/cataleg");
    }

    @Override
    public void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pwd = req.getParameter("password");

        resp.sendRedirect("/llibreria");
    }
}
