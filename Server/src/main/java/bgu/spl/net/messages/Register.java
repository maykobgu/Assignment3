package bgu.spl.net.messages;

public class Register implements Message {
    private String[] message;
    private short opCode;


    public Register(String message, short opCode) {
        this.message = message.split(" ");
        this.opCode = opCode;

    }

    @Override
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