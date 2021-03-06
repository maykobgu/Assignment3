package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class ConnectionsImpl<T> implements Connections<T> {
    private HashMap<Integer, ConnectionHandler> connections;




    @Override
    public boolean send(int connectionId, T msg) {
        ConnectionHandler connect = connections.get(connectionId);
        if (connect == null)
            return false;
        else {
            connect.send(msg);
            return true;
        }
    }

    @Override
    public void broadcast(T msg) {
    }

    @Override
    public void disconnect(int connectionId) {
        connections.remove(connectionId);
    }

    public void setConnection(int connectionId, ConnectionHandler handler) {
        connections.put(connectionId, handler);
    }

    public ConnectionHandler getConnection(int connectionId) {
        return connections.get(connectionId);
    }
}