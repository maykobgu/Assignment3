package bgu.spl.net.api;

import bgu.spl.net.api.bidi.messages.*;

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
        byte[] fullMessage;
        if (message instanceof Notification) {
            char type = ((Notification) message).getType();
            String postingUser = ((Notification) message).getUserName();
            String content = ((Notification) message).getContent();
            fullMessage = new byte[5 + postingUser.length() + content.length()];
            shortToBytes(message.getOpCode(), fullMessage);
            byte[] contentArr = (type + postingUser + "0" + content + "0").getBytes();
            int j = 0;
            for (int i = 2; i < fullMessage.length; i++) {
                fullMessage[i] = contentArr[j];
                j++;
            }
        } else if (message instanceof ErrorMsg) {
            fullMessage = new byte[4];
            shortToBytesForAckAndError(message.getOpCode(), ((ErrorMsg) message).getMsgOpCode(), fullMessage);
        } else {
            byte[] optionalArr = (((Ack) message).getOptional() + "\n").getBytes();
            int optionalArrSize = optionalArr.length;
            fullMessage = new byte[optionalArrSize + 4];
            shortToBytesForAckAndError(message.getOpCode(), ((Ack) message).getMsgOpCode(), fullMessage);
            int j = 0;
            for (int i = 4; i < fullMessage.length; i++) {
                fullMessage[i] = optionalArr[j];
                j++;
            }
        }
        return fullMessage;
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

    public void shortToBytesForAckAndError(short num, short num2, byte[] bytesArr) {
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        bytesArr[2] = (byte) ((num2 >> 8) & 0xFF);
        bytesArr[3] = (byte) (num2 & 0xFF);
    }

    public void shortToBytes(short num, byte[] bytesArr) {
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
    }

    private Message createMessage(short opCode, byte[] bytes) {
        String msg = popString();
        Message message;
        String username = "";
        String password = "";
        String submsg = msg.substring(2, msg.length() - 1);
        for (int i = 0; i < submsg.length(); i++) {
            if (submsg.charAt(i) == '0') {
                username = submsg.substring(0, i);
                password = submsg.substring(i + 1);
            }
        }
        switch (opCode) {
            case 1:
                message = new Register(username, password, opCode);
                break;
            case 2:
                message = new Login(username, password, opCode);
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
