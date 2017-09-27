package logique;

import java.util.ArrayList;
import java.util.Scanner;

public class Mastermind extends Game {

	public static int tour = 0;
	public static ArrayList<String> outcome = new ArrayList<String>();
	public static ArrayList<String> listePropositions = new ArrayList<String>();

	public Mastermind() {
		tour++;
	}

	Scanner sc = new Scanner(System.in);

	String afficheResultat(String combiD, String combiC) {
		
		String result = "";
		int x = 0; //nb de fois où le chiffre est présent dans la combi Challenger
		int y = 0; // nb de fois où le chiffre est present dans la combi Defenseur
		int bonnePlace = 0; // nb de chiffres bien places
		int present = 0; // nb de chiffres presents dans la combo mais à la mauvaise place
		String dejaVu = "";
		
		
		for (int i = 0; i < combiC.length() ; i++) {
			x = 0;
			y = 0;
			
			if (combiC.charAt(i) == combiD.charAt(i))
				bonnePlace++;
			
			for (int j = i ; j < combiC.length() ; j++) 
				if (dejaVu.indexOf(combiC.charAt(j)) == -1 && combiC.charAt(i) == combiC.charAt(j) ) 
					x++;
			
			for (int j = 0 ; j < combiD.length() ; j++)
				if ( combiC.charAt(i) == combiD.charAt(j))
					y++;
			
			present += (x <= y)? x : y;
				
			dejaVu += combiC.charAt(i);		
		}
		
		present -= bonnePlace;
		result = bonnePlace + " bien placé(s) et " + present + " présent(s)";
		return result;
	}

	protected Boolean modeChallenger(String combiD) {

		System.out.println("Proposez une combinaison: ");
		String combiC = sc.nextLine();

		System.out.println(this.afficheResultat(combiD, combiC));

		if (combiC.equals(combiD)) 
			return true;
		 else
			return false;
	}

	protected Boolean modeDefenseur(String combiD) {

		String combiC = combinaisonAleatoire(Main.nbDigits);
		
		System.out.println(combiC);
		System.out.println(this.afficheResultat(combiD, combiC));

		if (combiC.equals(combiD)) {
			System.out.println("L'ordinateur a gagné!");
			return true;
		} else
			return false;

	}

	protected Boolean modeDuel(String combiHumain, String combiOrdi) {
				
		Boolean winH = modeChallenger(combiOrdi);
		
		if (winH) {
			System.out.println("Vous avez gagné!");
			return true;
		}
		
		System.out.println(("\nL'ordinateur propose: "));
		Boolean winC = modeDefenseur(combiHumain);
		
		if(winC){
			System.out.println("L'ordinateur a gagné!");
			return true;
		}		
			
		return false;
	}

}
