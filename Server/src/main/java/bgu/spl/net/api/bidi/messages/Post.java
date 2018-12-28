package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.DataBase;
import bgu.spl.net.srv.User;

public class Post implements Message {
    private String message;
    private short opCode;


    public Post(String message, short opCode) {
        this.message = message;
        this.opCode = opCode;

    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol) {
        DataBase dataBase = protocol.getDataBase();
        Connections connections = protocol.getConnections();
        int connectionId = protocol.getConnectionId();
        short errorOp = 11;
        short ackOp = 10;
        String userName = protocol.getUserName();
        User thisUser = dataBase.getUser(userName);

        if (!dataBase.isLoggedIn(userName)) {
            ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
            connections.send(connectionId, error);
        } else {
            //find the @ in the content
            String[] content = message.split(" ");
            for (int i = 0; i < content.length; i++) {
                if (content[i].charAt(0) == '@') {
                    String user = content[i].substring(1);
                    //check if @ users are registered
                    if (dataBase.isRegistered(user)) {
                        Notification not = new Notification('1', userName, 0 + message);
                        connections.send(dataBase.getUser(user).getConnectionId(), not);// connectionId of the specific user
                        //send notification to these users
                    }
                }
            }
            //send notification to the followers list of the user
            for (String user : thisUser.getFollowersList()) {
                Notification not = new Notification('1', user, "0" + message+"0");
                connections.send(dataBase.getUser(user).getConnectionId(), not);// connectionId of the specific user
                // send notification to these users
            }
            //save the post in the user
            thisUser.addPost(message);
            Ack ack = new Ack(ackOp, getOpCode(), "");
            connections.send(connectionId, ack); //sent ACK for this specific user
        }
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}