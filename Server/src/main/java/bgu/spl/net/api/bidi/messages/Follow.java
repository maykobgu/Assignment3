package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.DataBase;

import java.util.LinkedList;

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
    public void process(BidiMessagingProtocolImpl protocol) {
        DataBase dataBase = protocol.getDataBase();
        Connections connections = protocol.getConnections();
        int connectionId = protocol.getConnectionId();
        short errorOp = 11;
        short ackOp = 10;
        short succededFollow = 0;
        short failedFollow = 0;
        String succeeded = "";
        if (!dataBase.isLoggedIn(protocol.getUserName())) {
            ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
            connections.send(connectionId, error);
        } else {
            if (type.equals("follow")) {
                for (String usr : getListOfUsersToFollow()) {
                    if (!dataBase.getUser(protocol.getUserName()).follow(usr))
                        failedFollow++;
                    else {
                        //check if user to follow exist??
                        succededFollow++;
                        succeeded = succeeded + usr + "0";
                    }
                }
            } else {
                for (String usr : getListOfUsersToFollow()) {
                    if (dataBase.getUser(protocol.getUserName()).unFollow(usr)) {
                        succededFollow++;
                        succeeded = succeeded + usr + "0";
                    } else
                        failedFollow++;
                }
            }
            if (failedFollow == numOfUsers) {
                ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
                connections.send(connectionId, error);
            } else {
                Ack ack = new Ack(ackOp, getOpCode(), succededFollow + succeeded);
                connections.send(connectionId, ack);
            }
        }
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