package bgu.spl.net.messages;

public class UserList implements Message {
    private short opCode;


    public UserList(short opCode) {
        this.opCode = opCode;

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