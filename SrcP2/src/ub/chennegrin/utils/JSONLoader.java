package ub.chennegrin.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ub.chennegrin.model.shop.Product;
import ub.chennegrin.model.shop.ShopManager;
import ub.chennegrin.model.users.User;
import ub.chennegrin.model.users.UsersManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by aaron on 21/05/2015.
 */
public class JSONLoader {

    public static void loadProductsFromJson(InputStream inputStream, ShopManager manager) throws FileNotFoundException {
        String json = readFile(inputStream);
        Gson gson = new Gson();
        final Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
        List<Product>  products = gson.fromJson(json, listType);
        for (Product product : products) {
            manager.addProduct(product);
        }
    }

    public static void loadUsersFromJson(InputStream inputStream, UsersManager manager) throws FileNotFoundException {
        String json = readFile(inputStream);
        Gson gson = new Gson();
        final Type listType = new TypeToken<ArrayList<User>>(){}.getType();
        List<User>  users = gson.fromJson(json, listType);
        for (User user : users) {
            manager.addUser(user);
        }
    }

    private static String readFile(InputStream inputStream) throws FileNotFoundException {
        Scanner sc = new Scanner(inputStream);
        StringBuilder buffer = new StringBuilder();
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine());
        }
        return buffer.toString();
    }
}
