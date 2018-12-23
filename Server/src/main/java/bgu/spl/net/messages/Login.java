package bgu.spl.net.messages;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.rci.Command;

import java.io.Serializable;

public class Login implements Message {
    private String message;


    public Login(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void process() {
        //do the login logic
    }
}