package bgu.spl.net.api;

import bgu.spl.net.messages.*;
import bgu.spl.net.messages.ErrorMsg;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl<T> implements MessageEncoderDecoder<Message> {
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
    public Message decodeNextByte(byte nextByte) {
        if (nextByte == '\n') {
            byte[] arr = new byte[2];
            arr[0] = bytes[0];
            arr[1] = bytes[1];
            short opCode = bytesToShort(arr);
            return createMessage(opCode, bytes);
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
    public byte[] encode(Message message) {
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


    private String popStringFrom(int offset) {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, offset, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }


    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    private Message createMessage(short opCode, byte[] bytes) {
        String msg = popString();
        Message message;
        switch (opCode) {
            case 1:
                message = new Register(msg.substring(2), opCode);
                break;
            case 2:
                message = new Login(msg.substring(2), opCode);
                break;
            case 3:
                message = new Logout(opCode);
                break;
            case 4:
                byte[] arr = new byte[2];
                arr[0] = bytes[3];
                arr[1] = bytes[4];
                short numOfUsers = bytesToShort(arr);
                String list = popStringFrom(6);
                message = new Follow(bytes[2], numOfUsers, list, opCode);
                break;
            case 5:
                message = new Post(msg.substring(2, msg.length() - 1), opCode);
                break;
            case 6:
                message = new PrivateMessage(msg.substring(2), opCode);
                break;
            case 7:
                message = new UserList(opCode);
                break;
            case 8:
                message = new Stat(msg.substring(2, msg.length() - 1), opCode);
                break;
            default:
                message = null;
                break;
        }

        return message;
    }
}
