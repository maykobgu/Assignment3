package bgu.spl.net.api.bidi;

import bgu.spl.net.messages.*;
import bgu.spl.net.srv.DataBase;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {
    private Connections connections;
    private int connectionId;
    private DataBase dataBase;
    private String userName;

    public BidiMessagingProtocolImpl(DataBase db) {
        dataBase = db;
    }

    /**
     * Used to initiate the current client protocol with it's personal connection ID and the connections implementation
     *
     * @param connectionId
     * @param connections
     */
    @Override
    public void start(int connectionId, Connections<Message> connections) {
        this.connections = connections;
        this.connectionId = connectionId;
    }

    @Override
    public void process(Message message) {
        short errorOp = 11;
        short ackOp = 10;
        if (message != null) {
            if (message instanceof Register) {
                if (dataBase.isRegistered(((Register) message).getUserName())) {
                    ErrorMsg error = new ErrorMsg(errorOp, message.getOpCode());
                    connections.send(connectionId, error);
                } else {
                    userName = ((Register) message).getUserName();
                    Ack ack = new Ack(ackOp, message.getOpCode(), "");
                    dataBase.registerUser(((Register) message).getUserName(), ((Register) message).getUserPassword());
                    connections.send(connectionId, ack);
                }
            } else if (message instanceof Login) {
                String usr = ((Login) message).getUserName();
                String pass = ((Login) message).getUserPassword();
                if (!dataBase.isRegistered(usr) || dataBase.getUserPassword(usr) != pass || dataBase.isLoggedIn(usr)) {
                    ErrorMsg error = new ErrorMsg(errorOp, message.getOpCode());
                    connections.send(connectionId, error);
                } else {
                    Ack ack = new Ack(ackOp, message.getOpCode(), "");
                    dataBase.loginUser(usr);
                    connections.send(connectionId, ack);
                }
            } else if (message instanceof Logout) {
                if (!dataBase.isRegistered(userName) || !dataBase.isLoggedIn(userName)) {
                    ErrorMsg error = new ErrorMsg(errorOp, message.getOpCode());
                    connections.send(connectionId, error);
                } else {
                    Ack ack = new Ack(ackOp, message.getOpCode(), "terminate"); //TODO in client
                    dataBase.logoutUser(userName);
                    connections.send(connectionId, ack);
                }
            } else if (message instanceof Follow) {
                String type = ((Follow) message).getFollowType();
                if (!dataBase.isLoggedIn(userName)) {
                    ErrorMsg error = new ErrorMsg(errorOp, message.getOpCode());
                    connections.send(connectionId, error);
                } else {
                    if (type.equals("follow")) {

                    }

                }
            }
        }
    }

    /**
     * @return true if the connection should be terminated
     */
    @Override
    public boolean shouldTerminate() {
        return false;
    }


}