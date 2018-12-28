package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public interface Message{
    void process(BidiMessagingProtocolImpl protocol);
    short getOpCode();
}
