package bgu.spl.net.messages;

public class Error implements Message {
    private String message;


    public Error(String message) {
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