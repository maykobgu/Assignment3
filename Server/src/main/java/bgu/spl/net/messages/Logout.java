package bgu.spl.net.messages;

public class Logout implements Message {
    private short opCode;


    public Logout(short opCode) {
        this.opCode = opCode;

    }

    @Override
    public String getMessage() {
        return "";
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