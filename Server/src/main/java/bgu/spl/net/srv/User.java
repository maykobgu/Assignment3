package bgu.spl.net.srv;

import java.util.LinkedList;

public class User {
    private String userName;
    private String password;
    private LinkedList followersList;
    private LinkedList followingList;
    private boolean login;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        followersList = new LinkedList();
        followingList = new LinkedList();
        login=false;
    }

    public String getPassword() {
        return password;
    }

    public LinkedList getFollowersList() {
        return followersList;
    }

    public LinkedList getFollowingList() {
        return followingList;
    }

    public String getUserName() {
        return userName;
    }

    public boolean follow(String userName) {
        if (followingList.contains(userName))
            return false;
        followingList.add(userName);
        return true;
    }

    public boolean isLogin() {
        return login;
    }

    public void login() {
        login=true;
    }

    public void logout() {
        login=false;
    }
}
