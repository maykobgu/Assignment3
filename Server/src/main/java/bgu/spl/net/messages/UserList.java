package bgu.spl.net.messages;

public class UserList implements Message {
    private String message;


    public UserList(String message) {
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