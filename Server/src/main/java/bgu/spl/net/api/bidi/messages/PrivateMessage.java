package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.DataBase;
import bgu.spl.net.srv.User;

public class PrivateMessage implements Message {
    private String userName;
    private String content;
    private short opCode;


    public PrivateMessage(String message, short opCode) {
        String[] m = message.split(" ");
        userName = m[0];
        content = m[1];
        this.opCode = opCode;

    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
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
                //send notification
                Notification not = new Notification('0', myUserName, "0" + content + "0");
                connections.send(dataBase.getUser(userName).getConnectionId(), not);// connectionId of the specific user

                //save the pm
                dataBase.getUser(myUserName).addPM(content);

                //return ack
                Ack ack = new Ack(ackOp, getOpCode(), "");
                connections.send(connectionId, ack); //sent ACK for this specific user
            }
        }
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}