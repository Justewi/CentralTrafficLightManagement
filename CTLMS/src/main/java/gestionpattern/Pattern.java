package gestionpattern;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Pattern {

    private ArrayList<Feu> feux;


    public Pattern() {
        feux = new ArrayList<Feu>();
    }

    public java.lang.String getDescription() {
        java.lang.String description = "{ \"pattern\" : ";
        if (feux.size() == 0) {
            description += "\"default\"";
        } else {
            description += "{ \"feux\" : [";
            for (Feu f : feux) {
                description += f.getDescription();
            }
            description += "] }";
        }

        description += " }";
        return description;
    }

    public ArrayList<Feu> getFeux() {
        return feux;
    }

    public void addFeu(Feu feu) {
        this.feux.add(feu);
    }
}
