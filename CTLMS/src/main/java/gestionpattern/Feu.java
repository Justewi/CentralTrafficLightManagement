package gestionpattern;

public abstract class Feu {

	protected int timeRed;
	protected int timeGreen;
	
	public Feu(int timeGreen, int timeRed) {
		this.timeRed = timeRed;
		this.timeGreen = timeGreen;
	}

	public int getTimeRed() {
		return timeRed;
	}

	public void setTimeRed(int timeRed) {
		this.timeRed = timeRed;
	}

	public int getTimeGreen() {
		return timeGreen;
	}

	public void setTimeGreen(int timeGreen) {
		this.timeGreen = timeGreen;
	}

	public abstract String getDescription();
	
}
