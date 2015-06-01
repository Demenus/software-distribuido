package ub.chennegrin.model.users;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by aaron on 21/05/2015.
 */
public class UsersManager {

    public static final double DEFAULT_USER_CURRENCY = 70.0;
    private static final UsersManager sUsersManager = new UsersManager();

    private final ConcurrentHashMap<String, User> mUsersMap = new ConcurrentHashMap<>();
    private int maxUserId;

    protected UsersManager() {
        maxUserId = -1;
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
        maxUserId = Math.max(user.getId(), maxUserId);
    }

    public User createNewUser(String username, double currency) {
        User u;
        synchronized (this) {
            int id = maxUserId + 1;
            u = new User(id, username, currency);
            addUser(u);
        }
        return u;
    }
}
