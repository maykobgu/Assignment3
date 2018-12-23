package bgu.spl.net.messages;

public class Register implements Message {
    private String message;


    public Register(String message) {
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