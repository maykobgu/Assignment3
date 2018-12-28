package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.DataBase;

public class UserList implements Message {
    private short opCode;


    public UserList(short opCode) {
        this.opCode = opCode;

    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol) {
        DataBase dataBase = protocol.getDataBase();
        Connections connections = protocol.getConnections();
        int connectionId = protocol.getConnectionId();
        short errorOp = 11;
        short ackOp = 10;
        String myUserName = protocol.getUserName();
        int size = dataBase.getUserList().size();
        String userlist = "";
        if (!dataBase.isLoggedIn(myUserName)) {
            ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
            connections.send(connectionId, error);
        } else {
            //return ack
            for (String usr : dataBase.getUserList()) {
                userlist = userlist + usr + "0";
            }
            Ack ack = new Ack(ackOp, getOpCode(), size + userlist);
            connections.send(connectionId, ack); //sent ACK for this specific user
        }
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}