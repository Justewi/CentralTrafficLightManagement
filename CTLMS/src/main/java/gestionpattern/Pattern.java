package gestionpattern;

public class Pattern {

    public static int DEFAULT_NORTH_SOUTH_AXIS_TIME = 20;
    public static int DEFAULT_EAST_WEST_AXIS_TIME = 20;

    private int nsAxisTime;
    private int ewAxisTime;
    private long startTime; // The start time in seconds


    public Pattern() {
        nsAxisTime = DEFAULT_NORTH_SOUTH_AXIS_TIME;
        ewAxisTime = DEFAULT_EAST_WEST_AXIS_TIME;
    }

    public Pattern(int nsAxisTime, int ewAxisTime, long startTime) {
        this.nsAxisTime = nsAxisTime;
        this.ewAxisTime = ewAxisTime;
        this.startTime = startTime;
    }

    public String getDescription() {
        String description = "pattern{";
        description += "\"NS\" : " + nsAxisTime + ", ";
        description += "\"EW\" : " + ewAxisTime + ","
                + "\"startTime\":" + startTime + " }";
        return description;
    }
}
