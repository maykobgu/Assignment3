package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public class BGSserver {

    public static void main(String[] args) {
        DataBase db = new DataBase(); //one shared object

// you can use any server... 
        Server.threadPerClient(
                7777, //port
                () -> new BidiMessagingProtocolImpl(db), //protocol factory
                MessageEncoderDecoderImpl::new //message encoder decoder factory
        ).serve();
//
//        Server.reactor(
//                Runtime.getRuntime().availableProcessors(),
//                7777, //port
//                () ->  new BidiMessagingProtocolImpl(db), //protocol factory
//                MessageEncoderDecoderImpl::new //message encoder decoder factory
//        ).serve();

    }
}
