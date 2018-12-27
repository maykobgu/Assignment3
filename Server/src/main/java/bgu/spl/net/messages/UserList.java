package bgu.spl.net.messages;

public class UserList implements Message {
    private String message;
    private short opCode;


    public UserList(String message, short opCode) {
        this.message = message;
        this.opCode = opCode;

    }

    @Override
    public String getMessage() {
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
}