package bgu.spl.net.srv;

import bgu.spl.net.messages.Message;
import bgu.spl.net.messages.Register;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataBase {
    private HashMap<String, User> registeredUsers;

    public DataBase() {
    }

    public boolean isRegistered(String userName) {
        if (registeredUsers.get(userName) != null) return true;
        return false;
    }

    public boolean isLoggedIn(String userName) {
        return registeredUsers.get(userName).isLogin();
    }

    public void registerUser(String userName, String password) {
        User user = new User(userName, password);
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

}
