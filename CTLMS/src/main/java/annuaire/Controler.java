package annuaire;

import org.springframework.data.annotation.Id;

public class Controler {

    @Id
    private java.lang.String flagId;
    private String pattern;

    protected Controler() {
    }

    public Controler(String flagId) {
        this.flagId = flagId;
    }

    public Controler(String flagId, String pattern) {
        this.flagId = flagId;
        this.pattern = pattern;
    }

    @Override
    public java.lang.String toString() {
        return "Controler{" +
                "flagId='" + flagId + '\'' +
                ", pattern=" + pattern +
                '}';
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public java.lang.String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

}
