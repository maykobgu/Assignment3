package bgu.spl.net.messages;

public class Stat implements Message {
    private String userName;
    private short opCode;


    public Stat(String message, short opCode) {
        userName = message;
        this.opCode = opCode;

    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void process() {
//logic
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}