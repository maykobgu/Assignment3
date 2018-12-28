package bgu.spl.net.messages;

public class PrivateMessage implements Message {
    private String userName;
    private String content;
    private short opCode;


    public PrivateMessage(String message, short opCode) {
        String[] m = message.split(" ");
        userName = m[0];
        content = m[1];
        this.opCode = opCode;

    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
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