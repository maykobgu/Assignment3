package bgu.spl.net.messages;

public class Ack implements Message {
    private short opCode;
    private short msgOpCode;
    private String optional;

    public Ack(short opCode, short msgOpCode, String optional) {
        this.opCode = opCode;
        this.msgOpCode = msgOpCode;
        this.optional = optional;
    }

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