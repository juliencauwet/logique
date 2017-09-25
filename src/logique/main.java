package logique;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static int nbDigits = 4;
	public static int chances = 8;
	public static Boolean dev = true;

	// **************MAIN******************
	public static void main(String[] args) {
		char recommencer = 'N';
		Scanner sc = new Scanner(System.in);

		do {
			lancement();
			System.out.println("Voulez-vous recommencer? (O/N)");
			recommencer = sc.nextLine().charAt(0);
		} while (recommencer == 'O');

		System.out.println("Merci et à bientôt!");
	}

	// **********LANCEMENT*****************
	public static void lancement() {
		int[] choix = new int[2];
		Scanner sc = new Scanner(System.in);
		int numeroTour = 1;
		String combiGagnante;
		Boolean gagne = false;

		System.out.println("Recherche +/- (1), Mastermind (2)?");
		choix[0] = sc.nextInt();
		System.out.println("Challenger (1), Défenseur (2), Duel (3)?");
		choix[1] = sc.nextInt();

		if (choix[1] == 2)
			combiGagnante = Game.proposerCombinaison();
		else
			combiGagnante = Game.combinaisonAleatoire(nbDigits);

		if (dev == true)
			System.out.println("combinaison gagnante: " + combiGagnante);

		do {
			gagne = jouer(choix, combiGagnante);
			numeroTour++;
		} while (numeroTour <= chances && gagne == false);

		if (!gagne)
			System.out.println("\nPerdu! La solution était: " + combiGagnante);

		miseAZero();
	}

	// *************JOUER******************
	static Boolean jouer(int[] option, String combiD) {

		Boolean win = false;

		if (option[0] == 1) {

			Recherche r = new Recherche();
			System.out.println("\nEssai n°" + Recherche.tour);

			if (option[1] == 1) {
				win = r.modeChallenger(combiD);
			} else if (option[1] == 2)
				win = r.modeDefenseur(combiD);
			else
				win = r.modeDuel(combiD);

		} else {

			Mastermind m = new Mastermind();
			System.out.println("\nEssai n°" + Mastermind.tour);

			if (option[1] == 1) {
				win = m.modeChallenger(combiD);
			} else if (option[1] == 2)
				win = m.modeDefenseur(combiD);
			else
				win = m.modeDuel(combiD);

		}

		return win;
	}

	// *************MISEAZERO******************
	static void miseAZero() {
		Recherche.tour = 0;
		Mastermind.tour = 0;

		for (int i = 0; i < Recherche.outcome.size(); i++) {
			Recherche.outcome.remove(i);
			Recherche.listePropositions.remove(i);
		}

		for (int j = 0; j < Mastermind.outcome.size(); j++) {
			Mastermind.outcome.remove(j);
			Mastermind.listePropositions.remove(j);
		}
	}
}
