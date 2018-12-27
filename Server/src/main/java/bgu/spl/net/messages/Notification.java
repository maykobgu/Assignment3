package bgu.spl.net.messages;

public class Notification implements Message {
    private String message;
    private short opCode;


    public Notification(String message, short opCode) {
        this.opCode = opCode;

        this.message = message;
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