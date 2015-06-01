package ub.chennegrin.controllers;

import ub.chennegrin.ServletDispatcher;
import ub.chennegrin.model.shop.Product;
import ub.chennegrin.model.shop.ShopManager;
import ub.chennegrin.model.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aaron on 27/05/2015.
 */
public class ProductsController extends PageController {
    @Override
    public void doGet(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processFileResponse(context, req, resp);
    }

    @Override
    public void doPost(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void processFileResponse(ServletDispatcher context, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productidStr = req.getParameter("productid");
        User user = (User) req.getAttribute("User");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }
        ShopManager manager = ShopManager.getInstance();
        try {
            int productId = Integer.valueOf(productidStr);
            if (user.hasPurchased(productId)) {
                Product product = manager.findProductById(productId);
                loadFile(product, context, resp);
            } else {
                resp.setStatus(403);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(404);
        }
    }

    private void loadFile(Product product, ServletDispatcher context, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + product.getFilename() + "\"");
        InputStream inputStream = context.getServletContext().getResourceAsStream("/WEB-INF/media/resources/"+product.getFilename());
        OutputStream outputStream = resp.getOutputStream();
        byte[] byteBuffer = new byte[1024];
        int length;
        while ((length = inputStream.read(byteBuffer)) != -1) {
            outputStream.write(byteBuffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}
