package ub.chennegrin.controllers;

import com.google.gson.Gson;
import ub.chennegrin.ServletDispatcher;
import ub.chennegrin.model.shop.CartList;
import ub.chennegrin.model.shop.Product;
import ub.chennegrin.model.shop.ShopManager;
import ub.chennegrin.model.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by aaron on 26/05/2015.
 */
public class CartListController extends PageController {
    private HashMap<String, String> mErrMap;
    private HashMap<String, String> mOkMap;
    private HashMap<String, String> mPurchasedMap;

    private Gson mGson;

    public CartListController() {
        mErrMap = new HashMap<>(3);
        mErrMap.put("result", "error");

        mOkMap = new HashMap<>(3);
        mOkMap.put("result", "ok");

        mPurchasedMap = new HashMap<>(3);
        mPurchasedMap.put("result", "purchased");

        mGson = new Gson();
    }

    @Override
    public void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setAttribute("Controller", this);
        req.getRequestDispatcher("/WEB-INF/jsp/protected/cartlist/cart-list.jsp").forward(req, resp);
    }

    @Override
    public void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String product = req.getParameter("productId");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (action == null) {
            resp.getWriter().write(mGson.toJson(mErrMap));
            return;
        }
        if (action.equalsIgnoreCase("add")) {
            addToCart(product, req, resp);
        } else if (action.equalsIgnoreCase("remove")) {
            removeFromCart(product, req, resp);
        } else if (action.equalsIgnoreCase("buy")) {
            buyProduct(product, req, resp);
        } else {
            resp.getWriter().write(mGson.toJson(mErrMap));
        }
    }

    private void buyProduct(String productStr, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CartList cart = (CartList) req.getSession().getAttribute("CartList");
        String numElems = String.valueOf(cart.getNumCartsElements());
        prepareResponseMap(numElems);
        try {
            int productId = Integer.valueOf(productStr);
            Product product = ShopManager.getInstance().findProductById(productId);
            User user = (User) req.getAttribute("User");
            if (user.hasPurchased(productId)) {
                mPurchasedMap.put("productId", productStr);
                resp.getWriter().write(mGson.toJson(mPurchasedMap));
            } else if (user.getCurrency() < product.getPrice()) {
                resp.getWriter().write(mGson.toJson(mErrMap));
            } else {
                cart.removeFromCart(productId);
                user.addPurchase(productId);
                user.decreaseCurrency(product.getPrice());
                mOkMap.put("numElems", String.valueOf(cart.getNumCartsElements()));
                mOkMap.put("productId", productStr);
                mOkMap.put("productPrice", String.valueOf(product.getPrice()));
                resp.getWriter().write(mGson.toJson(mOkMap));
            }
        } catch (NumberFormatException e) {
            resp.getWriter().write(mGson.toJson(mErrMap));
        }
    }

    private void prepareResponseMap(String numElems) {
        mErrMap.put("numElems", numElems);
        mOkMap.put("numElems", numElems);
        mPurchasedMap.put("numElems", numElems);
    }

    private void addToCart(String product, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CartList cart = (CartList) req.getSession().getAttribute("CartList");
        String numElems = String.valueOf(cart.getNumCartsElements());
        prepareResponseMap(numElems);
        try {
            int productId = Integer.valueOf(product);
            User user = (User) req.getAttribute("User");
            if (cart.isInCart(productId)) {
                resp.getWriter().write(mGson.toJson(mErrMap));
            } else if (user.hasPurchased(productId)) {
                mPurchasedMap.put("productId", product);
                resp.getWriter().write(mGson.toJson(mPurchasedMap));
            } else {
                cart.addToCart(productId);
                mOkMap.put("numElems", String.valueOf(cart.getNumCartsElements()));
                mOkMap.put("productId", product);
                resp.getWriter().write(mGson.toJson(mOkMap));
            }
        } catch (ClassCastException | NumberFormatException e) {
            resp.getWriter().write(mGson.toJson(mErrMap));
        }
    }

    private void removeFromCart(String product, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CartList cart = (CartList) req.getSession().getAttribute("CartList");
        String numElems = String.valueOf(cart.getNumCartsElements());
        prepareResponseMap(numElems);
        try {
            int productId = Integer.valueOf(product);
            User user = (User) req.getAttribute("User");
            if (!cart.isInCart(productId)) {
                resp.getWriter().write(mGson.toJson(mErrMap));
            } else if (user.hasPurchased(productId)) {
                mPurchasedMap.put("productId", product);
                resp.getWriter().write(mGson.toJson(mPurchasedMap));
            } else {
                cart.removeFromCart(productId);
                mOkMap.put("numElems", String.valueOf(cart.getNumCartsElements()));
                mOkMap.put("productId", product);
                resp.getWriter().write(mGson.toJson(mOkMap));
            }
        } catch (ClassCastException | NumberFormatException e) {
            resp.getWriter().write(mGson.toJson(mErrMap));
        }
    }
}
