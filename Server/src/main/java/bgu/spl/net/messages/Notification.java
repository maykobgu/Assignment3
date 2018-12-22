package bgu.spl.net.messages;

public class Notification implements Message {
    private String message;


    public Notification(String message) {
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