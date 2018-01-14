package annuaire;

import gestionpattern.FeuPieton;
import gestionpattern.FeuVoiture;

import java.util.ArrayList;

public class ControlerList {
    private static ArrayList<Controler> controlers = new ArrayList<Controler>();


    private static void initList() {
        String p = new String();
        ///p.addFeu(new FeuVoiture());
        ///p.addFeu(new FeuPieton());
        controlers.add(new Controler("ctrl1", "{ \"pattern\" : \"default\" }"));
        controlers.add(new Controler("ctrl2", "{ \"pattern\" : \"default\" }"));
        controlers.add(new Controler("ctrl3", "{ \"pattern\" : \"default\" }"));
    }

    public static ArrayList<Controler> getControlers() {
        return controlers;
    }

    public static int getSize() {
        return controlers.size();
    }

    public static Controler getControler(int id) {
        return controlers.get(id);
    }

    public static void sendInitPattern(QueueHandler qh) {
        try {
            qh.sendMessage("ALL","{ \"pattern\" : \"default\" }" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendModifiedPattern(QueueHandler qh) {
        for (Controler c : controlers) {
            try {
                qh.sendMessage(c.getFlagId(), c.getPattern());
            } catch (Exception e) {
                System.out.println("Couldn't send pattern at server init");
                e.printStackTrace();
            }
        }
    }

    static {
        ControlerList.initList();
    }
}