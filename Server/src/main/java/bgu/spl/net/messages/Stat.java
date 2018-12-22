package bgu.spl.net.messages;

public class Stat implements Message {
    private String message;


    public Stat(String message) {
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