package ctlms.model;

public class Pattern {

    public static int DEFAULT_NORTH_SOUTH_AXIS_TIME = 20;
    public static int DEFAULT_EAST_WEST_AXIS_TIME = 20;


    private int nsAxisTime;
    private int ewAxisTime;
    private long startTime; // The start time in miliseconds


    public Pattern() {
        nsAxisTime = DEFAULT_NORTH_SOUTH_AXIS_TIME;
        ewAxisTime = DEFAULT_EAST_WEST_AXIS_TIME;
    }

    public Pattern(int nsAxisTime, int ewAxisTime, long startTime) {
        this.nsAxisTime = nsAxisTime;
        this.ewAxisTime = ewAxisTime;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public String getDescription() {
        String description = "pattern{";
        description += "\"NS\" : " + nsAxisTime + ", ";
        description += "\"EW\" : " + ewAxisTime + ","
                + "\"startTime\":" + startTime + " }";
        return description;
    }

    public int getNsAxisTime() {
        return nsAxisTime;
    }

    public void setNsAxisTime(int nsAxisTime) {
        this.nsAxisTime = nsAxisTime;
    }

    public int getEwAxisTime() {
        return ewAxisTime;
    }

    public void setEwAxisTime(int ewAxisTime) {
        this.ewAxisTime = ewAxisTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
