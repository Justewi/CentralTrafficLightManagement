package annuaire;

public interface MessageListener {

    public void onMessage(String tag, byte[] body);
}
