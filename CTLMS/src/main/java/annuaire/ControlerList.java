package annuaire;

import gestionpattern.Pattern;

import java.util.ArrayList;

public class ControlerList {

    private static ArrayList<Controler> controlers = new ArrayList<Controler>();

    private static void initList() {
        Pattern p = new Pattern();
        controlers.add(new Controler("ctrl1", new Pattern(10, 10)));
        controlers.add(new Controler("ctrl2", new Pattern(10, 5)));
        controlers.add(new Controler("ctrl3", new Pattern(5, 20)));
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
            qh.sendMessage("ALL", new Pattern().getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendModifiedPattern(QueueHandler qh) {
        for (Controler c : controlers) {
            try {
                qh.sendMessage(c.getFlagId(), c.getPattern().getDescription());
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
