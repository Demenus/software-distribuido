package ub.chennegrin.model.shop;

import java.util.Date;

/**
 * Created by aaron on 25/05/2015.
 */
public class Purchase {
    private int id;
    private Date purchaseDate;
    private int productId;

    public Purchase() {

    }

    @Override
    public int hashCode() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() == Integer.class) return (Integer) o == productId;
        if (getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (id != purchase.id) return false;
        if (productId != purchase.productId) return false;
        if (purchaseDate != null ? !purchaseDate.equals(purchase.purchaseDate) : purchase.purchaseDate != null)
            return false;

        return true;
    }

    public int getId() {
        return id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", productId=" + productId +
                '}';
    }
}
