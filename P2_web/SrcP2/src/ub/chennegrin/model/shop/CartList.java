package ub.chennegrin.model.shop;

import ub.chennegrin.model.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by aaron on 26/05/2015.
 */
public class CartList {
    private CopyOnWriteArrayList<Integer> selectedProducts;
    private User user;

    public CartList() {
        selectedProducts = new CopyOnWriteArrayList<>();
    }

    public List<Product> getSelectedProducts() {
        ShopManager manager = ShopManager.getInstance();
        ArrayList<Product> products = new ArrayList<>(manager.getNumProducts());
        for (int productId : selectedProducts) {
            products.add(manager.findProductById(productId));
        }
        return products;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    public boolean isInCart(int productId) {
        return selectedProducts.contains(productId);
    }

    public void addToCart(int productId) {
        selectedProducts.add(productId);
    }

    public void removeFromCart(int productId) {
        int index = selectedProducts.indexOf(productId);
        selectedProducts.remove(index);
    }

    public int getNumCartsElements() {
        return selectedProducts.size();
    }
}
