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

	String afficheResultat(String combiDefenseur, String combiChallenger) {
		String strDejaVu = ""; // marqueur
		int bonnePlace = 0; // nb de chiffres bien places
		int present = 0; // nb de chiffres presents dans la combo mais à la mauvaise place
		String result = "";

		for (int j = 0; j < combiDefenseur.length(); j++) {

			int test = combiDefenseur.indexOf(combiChallenger.charAt(j)); // vérifie si le chiffre se trouve dans la
																			// combinaison de l'ordinateur

			if (test != -1 ) { // si le chiffre se trouve dans la combi de l'ordi et si
																// il n'a pas déjà été comptabilisé

				

				if (test == j)
					bonnePlace++;
				else if(strDejaVu.indexOf(combiChallenger.charAt(j)) != -1)
					present++;
				
				strDejaVu += test; // on archive l'index ou le chiffre a été trouvé dans tCombiOrdi
			}
		}

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

	protected Boolean modeDuel(String combiD) {
		
		Boolean winH = modeChallenger(combiD);
		
		if (winH) {
			System.out.println("Vous avez gagné!");
			return true;
		}
		
		System.out.println(("\nL'ordinateur propose: "));
		Boolean winC = modeDefenseur(combiD);
		
		if(winC){
			System.out.println("L'ordinateur a gagné!");
			return true;
		}		
			
		return false;
	}

}
