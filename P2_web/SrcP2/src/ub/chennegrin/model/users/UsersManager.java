package ub.chennegrin.model.users;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by aaron on 21/05/2015.
 */
public class UsersManager {

    private static final UsersManager sUsersManager = new UsersManager();

    private final ConcurrentHashMap<String, User> mUsersMap = new ConcurrentHashMap<>();

    protected UsersManager() {

    }

    public static UsersManager getInstance() {
        return sUsersManager;
    }

    public User findUserByName(String name) {
        if (name == null) {
            return null;
        }
        return mUsersMap.get(name);
    }

    public void addUser(User user) {
        mUsersMap.put(user.getUsername(), user);
    }
}
