package ctlms.model;

import org.springframework.data.annotation.Id;

public class Controler {

    @Id
    private String flagId;
    private Pattern pattern;
    private int x;
    private int y;


    protected Controler() {
    }

    public Controler(String flagId) {
        this.pattern=new Pattern();
        this.flagId = flagId;
    }

    public Controler(String flagId, Pattern pattern) {
        this.flagId = flagId;
        this.pattern = pattern;
    }

    public Controler(String flagId, int x, int y) {
        this(flagId);
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Controler{" +
                "flagId='" + flagId + '\'' +
                ", pattern=" + pattern +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public Controler(String flagId, Pattern pattern, int x, int y) {
        this(flagId, pattern);
        this.x = x;
        this.y = y;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public java.lang.String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
