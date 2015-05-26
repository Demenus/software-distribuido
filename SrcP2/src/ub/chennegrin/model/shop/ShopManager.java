package ub.chennegrin.model.shop;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by aaron on 13/05/2015.
 */
public class ShopManager {

    private static final ShopManager sInstance = new ShopManager();

    private final ConcurrentHashMap<Integer, Product> mMapProducts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<Product>> mByType = new ConcurrentHashMap<>();

    protected ShopManager() {
        init();
    }

    private void init() {
        mByType.put("Video", new CopyOnWriteArrayList<Product>());
        mByType.put("Audio", new CopyOnWriteArrayList<Product>());
        mByType.put("Book", new CopyOnWriteArrayList<Product>());
    }

    public static ShopManager getInstance() {
        return sInstance;
    }

    public void addProduct(Product product) {
        mMapProducts.put(product.getId(), product);
        List<Product> l = mByType.get(product.getType());
        l.add(product);
    }

    public Collection<Product> getAllProducts() {
        return mMapProducts.values();
    }

    public Product findProductById(int id) {
        return mMapProducts.get(id);
    }

    public List<Product> getProductsByType(String type) {
        return mByType.get(type);
    }
}
