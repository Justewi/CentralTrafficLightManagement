package annuaire;

import java.util.HashMap;

public class ControlerList{
    private static HashMap<Integer, String> controlers = new HashMap<Integer, String>();


    private static void initList(){
        controlers.put(0, "0");
        controlers.put(1, "1");
        controlers.put(2, "2");
    }

    public static HashMap<Integer, String> getControlers() {
        return controlers;
    }

    public static int getSize(){
        return controlers.size();
    }

    public static String getFlag(int id){
        return controlers.get(id);
    }

    static {
        ControlerList.initList();
    }
}