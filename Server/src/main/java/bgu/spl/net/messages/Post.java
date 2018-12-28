package bgu.spl.net.messages;

public class Post implements Message {
    private String message;
    private short opCode;


    public Post(String message, short opCode) {
        this.message = message;
        this.opCode = opCode;

    }

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