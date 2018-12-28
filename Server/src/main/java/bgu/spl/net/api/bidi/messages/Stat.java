package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.DataBase;
import bgu.spl.net.srv.User;

public class Stat implements Message {
    private String userName;
    private short opCode;


    public Stat(String message, short opCode) {
        userName = message;
        this.opCode = opCode;

    }

    public String getUserName() {
        return userName;
    }


    @Override
    public void process(BidiMessagingProtocolImpl protocol) {
        DataBase dataBase = protocol.getDataBase();
        Connections connections = protocol.getConnections();
        int connectionId = protocol.getConnectionId();
        short errorOp = 11;
        short ackOp = 10;
        String myUserName = protocol.getUserName();
        if (!dataBase.isLoggedIn(myUserName)) {
            ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
            connections.send(connectionId, error);
        } else {
            if (!dataBase.isRegistered(userName)) {
                ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
                connections.send(connectionId, error);
            } else {
                User releventUser = dataBase.getUser(userName);
                //return ack
                Ack ack = new Ack(ackOp, getOpCode(), (releventUser.getPosts().size()) + (releventUser.getFollowersList().size()) + (releventUser.getFollowingList().size()) + "");
                connections.send(connectionId, ack); //sent ACK for this specific user
            }
        }
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}