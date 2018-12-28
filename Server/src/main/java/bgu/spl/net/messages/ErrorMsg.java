package bgu.spl.net.messages;

public class ErrorMsg implements Message {
    private short opCode;
    private short msgOpCode;


    public ErrorMsg(short opCode, short msgOpCode) {
        this.opCode = opCode;
        this.msgOpCode = msgOpCode;
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