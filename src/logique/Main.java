package logique;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
	public static int nbDigits = Integer.parseInt(PropertiesFile.getPropertiesFile("nbDigits")); // nombre de cases
	public static int chances = Integer.parseInt(PropertiesFile.getPropertiesFile("chances")); // nombre d'essais
																								// possibles
	public static Boolean dev = Boolean.valueOf((PropertiesFile.getPropertiesFile("dev"))); // mode développeur

	// **************MAIN******************
	public static void main(String[] args) {
		char recommencer = 'N';
		Scanner sc = new Scanner(System.in);

		do {
			lancement();
			do {
				System.out.println("Voulez-vous recommencer? (O/N)");
				recommencer = sc.nextLine().charAt(0);
			} while (!reponseCorrecte(recommencer, "ON"));

		} while (recommencer == 'O');

		System.out.println("Merci et à bientôt!");
	}

	// pose les questions à l'utilisateur pour connaitre le jeu et le mode
	public static void lancement() {
		int[] choix = new int[2];
		Scanner sc = new Scanner(System.in);
		int numeroTour = 1;
		String combiGagnante;
		String combiDuel;
		Boolean gagne = false;
		
		
		
		choix = menu();
		
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
	
	//Menu: l'utilisateur choisit quel jeu, quel mode, si mode développeur

	static int[] menu() {
		int [] option = new int [2];
		Scanner sc = new Scanner(System.in);
		char rep;
		
		do {
			System.out.println("Souhaitez-vous jouer en mode développeur? (O/N)");
			rep = sc.nextLine().charAt(0);
		
			if (rep == 'O')
				Main.dev = true;
			else
				Main.dev = false;
		} while (!reponseCorrecte(rep, "ON")); 	
		
			
		do {
			System.out.println("A quel jeu souhaitez-vous jouer? Recherche +/- (1), Mastermind (2)?");
			option[0] = sc.nextInt();
		} while (!reponseCorrecte((char) (option[0] + '0'), "12"));
	
		do {
			System.out.println("Dans quel mode? Challenger (1), Défenseur (2), Duel (3)?");
			option[1] = sc.nextInt();
		} while (!reponseCorrecte((char) (option[1] + '0'), "123"));
		return option;
	}

	// commence le jeu en fonction du choix du menu
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

	// remet l'ensemble des paramètres à zéro pour recommencer une partie
	static void miseAZero() {
		Recherche.tour = 0;
		Mastermind.tour = 0;
		Recherche.listePropositions.clear();
		Mastermind.listePropositions.clear();
		Recherche.outcome.clear();
		Mastermind.outcome.clear();

	}

	// Vérifie si le choix entré est approprié
	static Boolean reponseCorrecte(char rep, String repPossible) {

		if (repPossible.indexOf(rep) == -1) {
			System.out.println("Veuillez s'il vous plaît entrer une des options proposées.");
			return false;
		} else
			return true;
	}
}
