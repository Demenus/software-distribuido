package ub.chennegrin;

import com.google.gson.Gson;
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
 * Created by aaron on 28/05/2015.
 */
public class WebServiceDispatcher extends HttpServlet {
    private HashMap<String, String> errMap = new HashMap<>();

    public WebServiceDispatcher() {
        errMap.put("RESULT","ERROR");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String endPoint = req.getRequestURI();
        if (req.getRequestURI().equals("/llibreria/consulta")) {
            llibreriaConsultaRequest(req, resp);
            return;
        }
        endPoint = endPoint.substring(1);
        String[] args = endPoint.split("/");
        if (args[0].equalsIgnoreCase("api")) {
            if (args[2].equalsIgnoreCase("cataleg")) {
                responseCataleg(args, req, resp);
            } else if (args[2].equalsIgnoreCase("item")) {
                responseItem(args, req, resp);
            } else {
                resp.setStatus(404);
                resp.getWriter().write("{}");
            }
        } else {
            resp.setStatus(404);
            resp.getWriter().write("{}");
        }
    }

    private void llibreriaConsultaRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/llibreria/consulta.jsp").forward(req, resp);
    }

    private static class ResponseObject {
        private String NAME;
        private String DESC;
        private float PRICE;
        private String LINK;

        public ResponseObject(String NAME, String DESC, float PRICE, String LINK) {
            this.NAME = NAME;
            this.DESC = DESC;
            this.PRICE = PRICE;
            this.LINK = LINK;
        }

        @Override
        public String toString() {
            return "ResponseObject{" +
                    "NAME='" + NAME + '\'' +
                    ", DESC='" + DESC + '\'' +
                    ", PRICE=" + PRICE +
                    ", LINK='" + LINK + '\'' +
                    '}';
        }
    }

    private void responseItem(String[] args, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = args[1];
        ShopManager manager = ShopManager.getInstance();
        Product p = manager.findProductByName(args[3]);
        Gson gson = new Gson();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin","*");
        if (p != null && p.getType().equalsIgnoreCase(type)) {
            ResponseObject object = new ResponseObject(p.getName(), p.getDesc(), p.getPrice(), generateLink(p, req));
            String json = gson.toJson(object);
            resp.getWriter().write(json);
        } else {
            resp.setStatus(404);
            resp.getWriter().write("{}");
        }
    }

    private String generateLink(Product p, HttpServletRequest req) {
        return "http://" + req.getServerName()+":"+req.getServerPort()+"/products/?productId=" + p.getId();
    }

    private void responseCataleg(String[] args, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = args[1].substring(0, 1).toUpperCase() + args[1].substring(1);
        ShopManager manager = ShopManager.getInstance();
        List<Product> products = manager.getProductsByType(type);
        ArrayList<HashMap<String, String>> res = new ArrayList<>();
        if (products != null) {
            for (Product p : products) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("NAME", p.getName());
                map.put("DESC", p.getDesc());
                res.add(map);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(res);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.getWriter().write(json);
    }
}
