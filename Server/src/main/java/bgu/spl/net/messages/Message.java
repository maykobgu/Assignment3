package bgu.spl.net.messages;

public interface Message<T>{
    void process();
    short getOpCode();
}