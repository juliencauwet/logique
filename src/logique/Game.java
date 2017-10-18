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
		String combiD = "";
		char rep;

		System.out.println();
		rep = saisieUtilisateur("combinaison aléatoire (1) ou personnalisée (2)?", "12");

		if (rep == '1')
			return Game.combinaisonAleatoire(Main.nbDigits);
		else if (rep == '2') {

			do {
				System.out.println("Veuillez entrer une combinaison à " + Main.nbDigits + " chiffres:");
				combiD = sc.next();
			} while (!combinaisonValide(combiD));
		}
			return combiD;

	}

	public static Boolean combinaisonValide(String combi) {

		for (int i = 0; i < combi.length(); i++) {
			if (combi.charAt(i) < '0' || combi.charAt(i) > '9' || combi.length() != Main.nbDigits) {
				System.out.println("Combinaison invalide.");
				return false;
			}
		}

		return true;
	}

	static char saisieUtilisateur(String question, String repPossible) {
		String rep = "";
		Scanner sc = new Scanner(System.in);

		do {

			try {
				System.out.println(question);
				rep = sc.nextLine();
			} catch (StringIndexOutOfBoundsException e) {
			}

			if (repPossible.indexOf(rep.charAt(0)) == -1 || rep.length() != 1)
				System.out.println("Veuillez s'il vous plaît entrer une des options proposées.\n");

		} while (repPossible.indexOf(rep.charAt(0)) == -1 || rep.length() != 1);

		return rep.charAt(0);
	}

}
