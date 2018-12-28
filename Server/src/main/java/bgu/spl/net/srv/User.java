package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String userName;
    private String password;
    private LinkedList<String> followersList;
    private LinkedList<String> followingList;
    private boolean login;
    private List<String> Posts;
    private List<String> PM;
    private int connectionId;


    public User(String userName, String password, int connectionId) {
        this.userName = userName;
        this.password = password;
        followersList = new LinkedList<>();
        followingList = new LinkedList<>();
        Posts = new LinkedList<>();
        PM = new LinkedList<>();
        login = false;
        this.connectionId = connectionId;
    }

    public String getPassword() {
        return password;
    }

    public LinkedList<String> getFollowersList() {
        return followersList;
    }

    public LinkedList<String> getFollowingList() {
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

    public boolean unFollow(String userName) {
        if (followingList.contains(userName)) {
            followingList.remove(userName);
            return true;
        }
        return false;
    }

    public boolean isLogin() {
        return login;
    }

    public void login() {
        login = true;
    }

    public void logout() {
        login = false;
    }

    public void addPost(String post) {
        Posts.add(post);
    }

    public void addPM(String msg) {
        PM.add(msg);
    }

    public int getConnectionId() {
        return connectionId;
    }

    public List<String> getPosts() {
        return Posts;
    }

    public List<String> getPM() {
        return PM;
    }
}
