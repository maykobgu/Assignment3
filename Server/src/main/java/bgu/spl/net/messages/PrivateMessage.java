package bgu.spl.net.messages;

public class PrivateMessage implements Message {
    private String message;


    public PrivateMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void process() {
        //do the login logic
    }
}