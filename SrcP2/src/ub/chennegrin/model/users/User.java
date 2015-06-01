package ub.chennegrin.model.users;

import ub.chennegrin.model.shop.CartList;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by aaron on 21/05/2015.
 */
public class User {
    private int id;
    private String username;
    private CopyOnWriteArrayList<Integer> purchased;
    private ConcurrentHashMap<String, CartList> cartLists;
    private double currency;

    public User(int id, String username, double currency) {
        this.id = id;
        this.username = username;
        this.currency = currency;
        init();
    }

    public User() {
        init();
    }

    private void init() {
        purchased = new CopyOnWriteArrayList<>();
        cartLists = new ConcurrentHashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Integer> getPurchased() {
        return purchased;
    }

    public double getCurrency() {
        return currency;
    }

    public void decreaseCurrency(double price) {
        currency = currency - price;
    }

    public void increaseCurrency(double price) {
        currency = currency + price;
    }

    public void addPurchase(int productId) {
        purchased.add(productId);
        for (CartList cartList :  cartLists.values()) {
            if (cartList.isInCart(productId)) {
                cartList.removeFromCart(productId);
            }
        }
    }

    public void addCartList(String sessionId, CartList cartList) {
        cartLists.put(sessionId, cartList);
    }

    public void removeCartList(String sessionId) {
        cartLists.remove(sessionId);
    }

    public boolean hasPurchased(int productId) {
        return purchased.contains(productId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", purchased=" + purchased +
                ", currency=" + currency +
                '}';
    }
}
