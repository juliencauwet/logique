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
				System.out.println("Veuillez entrer une combinaison:");
				combiD = sc.nextLine();
				return combiD;
			}
			
		} while (rep != '1' && rep != '2');
		
		return "Veuillez s'il vous plaît entrer une des options proposées.";
	}	

	abstract String afficheResultat(String combiDefenseur, String combiChallenger);

}
