package logique;

import java.util.Scanner;

abstract public class Game {

	abstract protected Boolean modeDefenseur(String combiD);

	abstract protected Boolean modeChallenger(String combiD);

	abstract protected Boolean modeDuel(String combiD, String combiOrdi);

	public static String combinaisonAleatoire(int nbDigits) {
		String combi = "";

		for (int i = 0; i < nbDigits; i++) {
			combi += (int) (Math.random() * 10);
		}

		return combi;
	}

	public static String proposerCombinaison() {

		Scanner sc = new Scanner(System.in);
		String combiD;
		char rep;

	
		do {
			System.out.println("combinaison aléatoire (1) ou personnalisée (2)?");
			rep = sc.nextLine().charAt(0);
			
			if (rep == '1')
				return Game.combinaisonAleatoire(Main.nbDigits);
			else if(rep == '2') {
				do {
					System.out.println("Veuillez entrer une combinaison à " + Main.nbDigits + " chiffres:");
					combiD = sc.nextLine();
				} while(!combinaisonValide(combiD));
					return combiD;				
			}else
				return "Veuillez s'il vous plaît entrer une des options proposées.";
			
			
			
		} while (rep != '1' && rep != '2');
		
	}	

	abstract String afficheResultat(String combiDefenseur, String combiChallenger);
	
	public static Boolean combinaisonValide(String combi) {
		
		if (combi.length() != Main.nbDigits) 
			return false;
		
		for (int i = 0; i < combi.length() ; i++) {
			if (combi.charAt(i) < '0' || combi.charAt(i) > '9')
				return false;			
		}
		
		return true;		
	}

}
