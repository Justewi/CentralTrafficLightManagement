package gestionpattern;

public class Pattern {
	private String description;

	public Pattern() {
		description = "default pattern\n";
	}

	public Pattern(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
