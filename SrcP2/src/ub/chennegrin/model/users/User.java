package ub.chennegrin.model.users;

import ub.chennegrin.model.shop.Product;
import ub.chennegrin.model.shop.Purchase;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by aaron on 21/05/2015.
 */
public class User {
    private int id;
    private String username;
    private CopyOnWriteArrayList<Integer> purchased;
    private float currency;

    public User() {
        purchased = new CopyOnWriteArrayList<>();
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

    public float getCurrency() {
        return currency;
    }

    public void addPurchase(int productId) {
        purchased.add(productId);
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
