package bgu.spl.net.messages;

public class Logout implements Message {
    private String message;


    public Logout(String message) {
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