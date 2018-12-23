package bgu.spl.net.messages;

public class Follow implements Message {
    private String message;
    private String type;

    public Follow(String message) {
        this.message = message;
        type = whichFollow(message);
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void process() {
        //do the login logic
    }

    private String whichFollow(String msg) {
        if (msg.charAt(0) == 0) return "follow";
        else return "unfollow";
    }
}