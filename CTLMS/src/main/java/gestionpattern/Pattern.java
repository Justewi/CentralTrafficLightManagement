package gestionpattern;

import java.util.ArrayList;

public class Pattern {

    public static int DEFAULT_NORTH_SOUTH_AXIS_TIME = 20;
    public static int DEFAULT_EAST_WEST_AXIS_TIME = 20;

    private int nsAxisTime;
    private int ewAxisTime;

    public Pattern() {
        nsAxisTime = DEFAULT_NORTH_SOUTH_AXIS_TIME;
        ewAxisTime = DEFAULT_EAST_WEST_AXIS_TIME;
    }

    public Pattern(int nsAxisTime, int ewAxisTime) {
        this.nsAxisTime = nsAxisTime;
        this.ewAxisTime = ewAxisTime;
    }

    public String getDescription() {
        String description = "\"pattern\" {";
        description += "\"NS\" : "+nsAxisTime + ", ";
        description += "\"EW\" : "+ewAxisTime + " }";
        return description;
    }
}
