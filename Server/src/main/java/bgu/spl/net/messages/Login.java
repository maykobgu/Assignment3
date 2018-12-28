package bgu.spl.net.messages;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Login implements Message {
    private String[] message;
    private short opCode;

    public Login(String message, short opCode) {
        this.message = message.split(" ");
        this.opCode = opCode;

    }

    public String[] getMessage() {
        return message;
    }

    @Override
    public void process() {
//logic
    }

    @Override
    public short getOpCode() {
        return opCode;
    }


    public String getUserName() {
        return message[0];
    }

    public String getUserPassword() {
        return message[1];
    }

}