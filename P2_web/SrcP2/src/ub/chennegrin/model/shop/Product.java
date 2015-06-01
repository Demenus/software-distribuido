package ub.chennegrin.model.shop;

/**
 * Created by aaron on 13/05/2015.
 */
public class Product {
    private int id;
    private String name;
    private float price;
    private String desc;
    private String type;
    private int reviews;
    private float stars;
    private String filename;

    public Product() {

    }

    public Product(int id, String name, float price, String desc, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public int getReviews() {
        return reviews;
    }

    public float getStars() {
        return stars;
    }

    public int getStarsInt() {
        return (int) Math.ceil(stars);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", reviews=" + reviews +
                ", stars=" + stars +
                ", filename='" + filename + '\'' +
                '}';
    }
}
