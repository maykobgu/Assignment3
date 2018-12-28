package bgu.spl.net.messages;

public class Follow implements Message {
    private String[] usersList;
    private short opCode;
    private String type;
    private short numOfUsers;

    public Follow(int followType, short numOfUsers, String list, short opCode) {
        if (followType == 0) type = "follow";
        else type = "unfollow";
        this.opCode = opCode;
        this.numOfUsers = numOfUsers;
        this.usersList = list.split(" ");
    }

    public String[] getListOfUsersToFollow() {
        return usersList;
    }

    @Override
    public void process() {
//logic
    }

    @Override
    public short getOpCode() {
        return opCode;
    }

    public String getFollowType() {
        return type;
    }

    public short getNumOfUsers() {
        return numOfUsers;
    }

}