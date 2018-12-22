package bgu.spl.net.messages;

public class Ack implements Message {
    private String message;


    public Ack(String message) {
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