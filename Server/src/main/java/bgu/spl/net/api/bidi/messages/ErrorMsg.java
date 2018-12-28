package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public class ErrorMsg implements Message {
    private short opCode;
    private short msgOpCode;


    public ErrorMsg(short opCode, short msgOpCode) {
        this.opCode = opCode;
        this.msgOpCode = msgOpCode;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol) {
    }

    public short getMsgOpCode() {
        return msgOpCode;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}