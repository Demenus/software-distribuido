package ub.chennegrin.model.shop;

import java.util.ArrayList;

/**
 * Created by aaron on 26/05/2015.
 */
public class CartList {
    private ArrayList<Integer> selectedProducts;

    public CartList() {
        selectedProducts = new ArrayList<>();
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
