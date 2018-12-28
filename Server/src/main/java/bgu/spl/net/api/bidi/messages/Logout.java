package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.DataBase;

public class Logout implements Message {
    private short opCode;


    public Logout(short opCode) {
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
        if (!dataBase.isLoggedIn(userName)) {
            ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
            connections.send(connectionId, error);
        } else {
            Ack ack = new Ack(ackOp, getOpCode(), "terminate"); //TODO in client
            dataBase.logoutUser(userName);
            connections.send(connectionId, ack);
        }
    }

    @Override
    public short getOpCode() {
        return opCode;
    }

}