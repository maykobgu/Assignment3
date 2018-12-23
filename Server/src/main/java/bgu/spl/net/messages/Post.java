package bgu.spl.net.messages;

public class Post implements Message {
    private String message;


    public Post(String message) {
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