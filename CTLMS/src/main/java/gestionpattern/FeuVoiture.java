package gestionpattern;

public class FeuVoiture extends Feu {

	public static int DEFAULT_GREEN_DURATION = 10;
	public static int DEFAULT_ORANGE_DURATION = 3;
	public static int DEFAULT_RED_DURATION = 20;
	
	protected int timeOrange;
	
	public FeuVoiture(){
		super(DEFAULT_GREEN_DURATION, DEFAULT_RED_DURATION);
		this.timeOrange = DEFAULT_ORANGE_DURATION;
	}
	
	public FeuVoiture(int timeGreen, int timeRed) {
		super(timeGreen, timeRed);
		this.timeOrange = DEFAULT_ORANGE_DURATION;
	}
	
	public FeuVoiture(int timeGreen, int timeOrange, int timeRed) {
		super(timeGreen, timeRed);
		this.timeOrange = timeOrange;
	}

	public int getTimeOrange() {
		return timeOrange;
	}

	public void setTimeOrange(int timeOrange) {
		this.timeOrange = timeOrange;
	}
	

}
