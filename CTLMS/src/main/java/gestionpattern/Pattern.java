package gestionpattern;

import java.util.ArrayList;

public class Pattern {

	private ArrayList<Feu> feux;

	public Pattern() {
		feux = new ArrayList<Feu>();
	}

	public String getDescription() {
		String description = "{ \"pattern\" : ";
		if(feux.size() == 0){
			description += "\"default\"";
		}else{
			description += "{ \"feux\" : [";
			for(Feu f : feux){
				description += f.getDescription();
			}
			description += "] }";
		}

		description += " }";
		return description;
	}

	public ArrayList<Feu> getFeux() {
		return feux;
	}

	public void addFeu(Feu feu) {
		this.feux.add(feu);
	}
}
