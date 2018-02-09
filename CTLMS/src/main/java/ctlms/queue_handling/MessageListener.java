package ctlms.queue_handling;

public interface MessageListener {

    public void onMessage(String tag, byte[] body);
}
