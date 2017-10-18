package logique;

import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main extends Exception {
	private static final Logger logger = Logger.getLogger(Main.class);

	public static int nbDigits = Integer.parseInt(PropertiesFile.getPropertiesFile("nbDigits")); // nombre de cases
	public static int chances = Integer.parseInt(PropertiesFile.getPropertiesFile("chances")); // nombre d'essais
																								// possibles
	public static Boolean dev = Boolean.valueOf((PropertiesFile.getPropertiesFile("dev"))); // mode développeur

	// **************MAIN******************
	public static void main(String[] args) {
		char recommencer = 'N';

		do {
			logger.log(Level.INFO, "Le logiciel s'est mis en marche avec succès.");

			lancement();

			recommencer = saisieUtilisateur("Voulez-vous recommencer? (O/N)", "OoNn");

		} while (recommencer == 'O' || recommencer == 'o');

		System.out.println("Merci et à bientôt!");
	}

	/** pose les questions à l'utilisateur pour connaitre le jeu et le mode */
	public static void lancement() {
		int[] choix = menu();
		int numeroTour = 1;
		String combiGagnante;
		String combiDuel;
		Boolean gagne = false;

		if (choix[1] == 2 || choix[1] == 3)
			combiGagnante = Game.proposerCombinaison();
		else
			combiGagnante = Game.combinaisonAleatoire(nbDigits);

		combiDuel = Game.combinaisonAleatoire(nbDigits);

		if (dev == true) {
			System.out.println("combinaison gagnante: " + combiGagnante);

			if (choix[1] == 3)
				System.out.println("La combinaison gagnante générée par l'ordinateur est: " + combiDuel);
		}

		do {
			gagne = jouer(choix, combiGagnante, combiDuel);
			numeroTour++;
		} while (numeroTour <= chances && gagne == false);

		if (!gagne)
			System.out.println("\nPerdu! La solution était: " + combiGagnante);

		miseAZero();
	}

	/** Menu: l'utilisateur choisit quel jeu, quel mode, si mode développeur */

	static int[] menu() {
		int[] option = new int[2];
		char rep;

		rep = saisieUtilisateur("Souhaitez-vous jouer en mode développeur? (O/N)", "oOnN");

		if (rep == 'O' || rep == 'o')
			Main.dev = true;
		else
			Main.dev = false;

		rep = saisieUtilisateur("A quel jeu souhaitez-vous jouer? Recherche +/- (1), Mastermind (2)?", "12");
		option[0] = Character.getNumericValue(rep);

		rep = saisieUtilisateur("Dans quel mode? Challenger (1), Défenseur (2), Duel (3)?", "123");
		option[1] = Character.getNumericValue(rep);

		return option;
	}

	/**
	 * commence le jeu en fonction du choix du menu
	 * 
	 * @param option
	 *            tableau des choix du jeu et du mode
	 * @param combiD
	 *            est la combianison générée par l'ordinateur que doit trouver le
	 *            Challenger
	 * @param combiDuel
	 *            sert en mode Duel
	 */
	static Boolean jouer(int[] option, String combiD, String combiDuel) {

		Boolean win = false;

		if (option[0] == 1) {

			Recherche r = new Recherche();
			System.out.println("\nEssai n°" + Recherche.tour);

			if (option[1] == 1) {
				win = r.modeChallenger(combiD);
			} else if (option[1] == 2)
				win = r.modeDefenseur(combiD);
			else
				win = r.modeDuel(combiD, combiDuel);

		} else {

			Mastermind m = new Mastermind();
			System.out.println("\nEssai n°" + Mastermind.tour);

			if (option[1] == 1) {
				win = m.modeChallenger(combiD);
			} else if (option[1] == 2)
				win = m.modeDefenseur(combiD);
			else
				win = m.modeDuel(combiD, combiDuel);

		}

		return win;
	}

	/** remet l'ensemble des paramètres à zéro pour recommencer une partie */
	static void miseAZero() {
		Recherche.tour = 0;
		Mastermind.tour = 0;
		Recherche.listePropositions.clear();
		Mastermind.listePropositions.clear();
		Recherche.outcome.clear();
		Mastermind.outcome.clear();
		Mastermind.chiffresTrouves = 0;

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
			rep = sc.next();

			// si la réponse n'est pas une option proposée et si la taille de la réponse
			// est différente de 1, affiche message d'erreur et repose la question
			if (repPossible.indexOf(rep.charAt(0)) == -1 || rep.length() != 1)
				System.out.println("Veuillez s'il vous plaît entrer une des options proposées.\n");

		} while (repPossible.indexOf(rep.charAt(0)) == -1 || rep.length() != 1);

		return rep.charAt(0);
	}
}
