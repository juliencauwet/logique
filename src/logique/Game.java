package logique;

import java.util.Scanner;

/** Classe mère des 2 jeux Recherche et Mastermind */
abstract public class Game {

	abstract protected Boolean modeDefenseur(String combiD);

	abstract protected Boolean modeChallenger(String combiD);

	abstract protected Boolean modeDuel(String combiD, String combiOrdi);

	/**
	 * Génère une combinaison aléatoire
	 * 
	 * @param nbDigits
	 *            nombre de chiffres dans la combinaison défini dans les parametres
	 * @return la combinaison
	 */
	public static String combinaisonAleatoire(int nbDigits) {
		String combi = "";

		for (int i = 0; i < nbDigits; i++) {
			combi += (int) (Math.random() * 10);
		}

		return combi;
	}

	/**
	 * Demande à l'utilisteur s'il souhaite générer une combinaison aléatoire ou
	 * personnalisée En fontion, appelle une combi aléatoire ou saisit la
	 * combinaison de l'utilisateur
	 * 
	 * @see Game#saisieUtilisateur(String, String)
	 * @see Game#combinaisonAleatoire(int)
	 * @see Game#combinaisonValide(String)
	 * @return combinaison que l'ordinateur doit trouver
	 */
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

	/**
	 * Vérifie que la combinaison est conforme aux paramètre et envoie un message
	 * d'erreur si elle ne l'est pas
	 */
	public static Boolean combinaisonValide(String combi) {

		for (int i = 0; i < combi.length(); i++) {
			if (combi.charAt(i) < '0' || combi.charAt(i) > '9' || combi.length() != Main.nbDigits) {
				System.out.println("Combinaison invalide.");
				return false;
			}
		}

		return true;
	}

	/**
	 * affiche la question, saisit entrée utilisateur Vérifie si le choix entré par
	 * l'utilisateur est approprié
	 * 
	 * @param question
	 *            à afficher
	 * @param repPossible
	 *            ensemble des choix possibles sous forme de String
	 * @return le choix de l'utilisateur
	 */
	static char saisieUtilisateur(String question, String repPossible) {
		String rep = "";
		Scanner sc = new Scanner(System.in);

		do {

			System.out.println(question);
			rep = sc.nextLine();

			if (repPossible.indexOf(rep.charAt(0)) == -1 || rep.length() != 1)
				System.out.println("Veuillez s'il vous plaît entrer une des options proposées.\n");

		} while (repPossible.indexOf(rep.charAt(0)) == -1 || rep.length() != 1);

		return rep.charAt(0);
	}

}
