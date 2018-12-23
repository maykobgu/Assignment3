package bgu.spl.net.api;

import bgu.spl.net.messages.*;
import bgu.spl.net.messages.Error;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl<T> implements MessageEncoderDecoder {
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    /**
     * add the next byte to the decoding process
     *
     * @param nextByte the next byte to consider for the currently decoded
     *                 message
     * @return a message if this byte completes one or null if it doesnt.
     */
    @Override
    public Object decodeNextByte(byte nextByte) {
        if (nextByte == '\n') {
            byte[] arr = new byte[2];
            arr[0] = bytes[0];
            arr[1] = bytes[1];
            short opCode = bytesToShort(arr);
            String msg = popString();
            return createMessage(opCode, msg.substring(2));
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    /**
     * encodes the given message to bytes array
     *
     * @param message the message to encode
     * @return the encoded bytes
     */
    @Override
    public byte[] encode(Object message) {
        return (message + "\n").getBytes();
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    private Message createMessage(short opCode, String msg) {
        Message message;
        switch (opCode) {
            case 1:
                message = new Register(msg);
                break;
            case 2:
                message = new Login(msg);
                break;
            case 3:
                message = new Logout(msg);
                break;
            case 4:
                message = new Follow(msg);
                break;
            case 5:
                message = new Post(msg);
                break;
            case 6:
                message = new PrivateMessage(msg);
                break;
            case 7:
                message = new UserList(msg);
                break;
            case 8:
                message = new Stat(msg);
                break;
            case 9:
                message = new Notification(msg);
                break;
            case 10:
                message = new Ack(msg);
                break;
            case 11:
                message = new Error(msg);
                break;
            default:
                message = null;
                break;
        }
        return message;
    }


    abstract
}
