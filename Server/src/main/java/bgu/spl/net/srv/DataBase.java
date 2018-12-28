package bgu.spl.net.srv;

import java.util.*;

public class DataBase {
    private HashMap<String, User> registeredUsers;
    private LinkedList<String> userList;

    public DataBase() {
        registeredUsers = new HashMap<>();
        userList = new LinkedList<>();
    }

    public boolean isRegistered(String userName) {
        if (registeredUsers.get(userName) != null) return true;
        return false;
    }

    public boolean isLoggedIn(String userName) {
        return registeredUsers.get(userName).isLogin();
    }

    public void registerUser(String userName, String password, int connectionId) {
        User user = new User(userName, password, connectionId);
        userList.add(userName);
    }

    public void loginUser(String userName) {
        registeredUsers.get(userName).login();
    }

    public void logoutUser(String userName) {
        registeredUsers.get(userName).logout();
    }

    public String getUserPassword(String userName) {
        return registeredUsers.get(userName).getPassword();
    }

    public User getUser(String name) {
        return registeredUsers.get(name);
    }

    public LinkedList<String> getUserList() {
        return userList;
    }
}
