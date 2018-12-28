package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public class Ack implements Message {
    private short opCode;
    private short msgOpCode;
    private String optional;

    public Ack(short opCode, short msgOpCode, String optional) {
        this.opCode = opCode;
        this.msgOpCode = msgOpCode;
        this.optional = optional;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol) {
    }

    public String getOptional() {
        return optional;
    }

    public short getMsgOpCode() {
        return msgOpCode;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}