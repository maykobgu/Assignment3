package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public class Notification implements Message {
    private short opCode;
    private char type;
    private String userName;
    private String content;


    public Notification(char type, String userName, String content) {
        this.opCode = opCode;
        this.type = type;
        this.userName = userName;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol) {
    }


    public String getUserName() {
        return userName;
    }

    public char getType() {
        return type;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}