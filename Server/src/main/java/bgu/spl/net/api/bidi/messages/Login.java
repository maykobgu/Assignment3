package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.DataBase;

public class Login implements Message {
    private String username;
    private String password;
    private short opCode;

    public Login(String user, String pass, short opCode) {
        this.username = user;
        this.password = pass;
        this.opCode = opCode;

    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol) {
        DataBase dataBase = protocol.getDataBase();
        Connections connections = protocol.getConnections();
        int connectionId = protocol.getConnectionId();
        short errorOp = 11;
        short ackOp = 10;


        String usr = getUserName();
        String pass = getUserPassword();
        if (!dataBase.isRegistered(usr) || dataBase.getUserPassword(usr) != pass || dataBase.isLoggedIn(usr)) {
            ErrorMsg error = new ErrorMsg(errorOp, getOpCode());
            connections.send(connectionId, error);
        } else {
            Ack ack = new Ack(ackOp, getOpCode(), "");
            dataBase.loginUser(usr);
            connections.send(connectionId, ack);
        }

    }

    @Override
    public short getOpCode() {
        return opCode;
    }


    public String getUserName() {
        return username;
    }

    public String getUserPassword() {
        return password;
    }

}