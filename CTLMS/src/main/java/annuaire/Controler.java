package annuaire;

import gestionpattern.Pattern;

public class Controler {

    private String flagId;
    private Pattern pattern;

    public Controler(String flagId) {
        this.flagId = flagId;
    }

    public Controler(String flagId, Pattern pattern) {
        this.flagId = flagId;
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

}
