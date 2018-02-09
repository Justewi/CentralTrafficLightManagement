package ctlms.model;

public class Pattern {

    public enum Direction {
        NS, EW
    };
    public static final int DEFAULT_NORTH_SOUTH_AXIS_TIME = 40;
    public static final int DEFAULT_EAST_WEST_AXIS_TIME = 40;
    public static final Direction DEFAULT_START_DIRECTION = Direction.NS;


    private int nsAxisTime;
    private int ewAxisTime;
    private long startTime; // The start time in miliseconds
    private Direction startDirection;

    public Pattern() {
        nsAxisTime = DEFAULT_NORTH_SOUTH_AXIS_TIME;
        ewAxisTime = DEFAULT_EAST_WEST_AXIS_TIME;
        startDirection = DEFAULT_START_DIRECTION;
    }

    public Pattern(int nsAxisTime, int ewAxisTime, long startTime, Direction startDirection) {
        this.nsAxisTime = nsAxisTime;
        this.ewAxisTime = ewAxisTime;
        this.startTime = startTime;
        this.startDirection = startDirection;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public String getDescription() {
        String description = "pattern{";
        description += "\"NS\" : " + nsAxisTime + ", ";
        description += "\"EW\" : " + ewAxisTime + ","
                + "\"startTime\":" + startTime + ","
                + "\"startDirection\": " + "\"" + startDirection.name() + "\"" + " }";
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

    public Direction getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(Direction startDirection) {
        this.startDirection = startDirection;
    }
}
