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
import java.util.Collection;
import java.util.HashMap;

/**
 * /*The controller which process the request and response in the Cataleg webpage.
 */
public class CatalegController extends PageController {
    private String err = "{\"result\":\"error\"}";
    private String ok = "{\"result\":\"ok\"}";
    private String purchased = "{\"result\":\"purchased\"}";

    private HashMap<String, String> mErrMap;
    private HashMap<String, String> mOkMap;
    private HashMap<String, String> mPurchasedMap;

    private Gson mGson;

    public CatalegController() {
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
        req.getRequestDispatcher("/WEB-INF/jsp/cataleg/llibreria-cataleg.jsp").forward(req, resp);
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
        } else {
            resp.getWriter().write(mGson.toJson(mErrMap));
        }
    }

    private void prepareResponseMap(String numElems) {
        mErrMap.put("numElems", numElems);
        mOkMap.put("numElems", numElems);
        mPurchasedMap.put("numElems", numElems);
    }

    /*It handles the action of add product to cart in the cataleg page. */
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
                resp.getWriter().write(mGson.toJson(mPurchasedMap));
            } else {
                cart.addToCart(productId);
                mOkMap.put("numElems", String.valueOf(cart.getNumCartsElements()));
                resp.getWriter().write(mGson.toJson(mOkMap));
            }
        } catch (ClassCastException | NumberFormatException e) {
            resp.getWriter().write(mGson.toJson(mErrMap));
        }
    }
    /*It handles the action of remove products from the cart in the cataleg page. */
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
                resp.getWriter().write(mGson.toJson(mPurchasedMap));
            } else {
                cart.removeFromCart(productId);
                mOkMap.put("numElems", String.valueOf(cart.getNumCartsElements()));
                resp.getWriter().write(mGson.toJson(mOkMap));
            }
        } catch (ClassCastException | NumberFormatException e) {
            resp.getWriter().write(mGson.toJson(mErrMap));
        }
    }

    public Collection<Product> getAllProducts() {
        return ShopManager.getInstance().getAllProducts();
    }
}
