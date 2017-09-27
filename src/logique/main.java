package logique;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
	public static int nbDigits = Integer.parseInt(PropertiesFile.getPropertiesFile("nbDigits"));
	public static int chances = Integer.parseInt(PropertiesFile.getPropertiesFile("chances"));
	public static Boolean dev = Boolean.valueOf((PropertiesFile.getPropertiesFile("dev")));

	// **************MAIN******************
	public static void main(String[] args) {
		char recommencer = 'N';
		Scanner sc = new Scanner(System.in);
		
		do {
			lancement();
			do{
				System.out.println("Voulez-vous recommencer? (O/N)");
				recommencer = sc.nextLine().charAt(0);				
			}while (!reponseCorrecte(recommencer,"ON"));
			
		} while (recommencer == 'O');

		System.out.println("Merci et à bientôt!");
	}

	// **********LANCEMENT*****************
	public static void lancement() {
		int[] choix = new int[2];
		Scanner sc = new Scanner(System.in);
		int numeroTour = 1;
		String combiGagnante;
		String combiDuel;
		Boolean gagne = false;

		System.out.println("Recherche +/- (1), Mastermind (2)?");
		choix[0] = sc.nextInt();
		System.out.println("Challenger (1), Défenseur (2), Duel (3)?");
		choix[1] = sc.nextInt();

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

	// *************JOUER******************
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
	
	static Boolean reponseCorrecte(char rep, String repPossible ) {
		
		if (repPossible.indexOf(rep) == -1)
			return false;
		else 
			return true;
	}
}





