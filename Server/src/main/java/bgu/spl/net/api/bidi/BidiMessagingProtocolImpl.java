package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.messages.*;
import bgu.spl.net.srv.DataBase;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {
    private Connections connections;
    private int connectionId;
    private String userName;
    private boolean terminate;
    private DataBase dataBase;

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
        terminate = false;
    }

    @Override
    public void process(Message message) {
        message.process(this);
    }

    /**
     * @return true if the connection should be terminated
     */
    @Override
    public boolean shouldTerminate() {
        return terminate;
    }

    public void Terminate() {
        terminate = true;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Connections getConnections() {
        return connections;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}