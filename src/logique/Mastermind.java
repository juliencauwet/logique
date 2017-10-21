package logique;

import java.util.ArrayList;

import java.util.Scanner;

/** Classe qui contien toutes les méthodes nécessaires au jeu Mastermind */
public class Mastermind extends Game {

	public static int tour = 0;
	public static int chiffresTrouves = 0;
	public static ArrayList<String> outcome = new ArrayList<String>();
	public static ArrayList<String> listePropositions = new ArrayList<String>();
	public static char[] chiffres = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static ArrayList<String> listeCombi = new ArrayList<String>();

	public Mastermind() {
		tour++;
	}

	Scanner sc = new Scanner(System.in);

	/**
	 * affiche le nombre de chiffres de la combinaison du Challenger qui sont
	 * présents ou à la bonne place dans la combinaison du défenseur
	 * 
	 * @param combiD
	 *            combinaison du défenseur
	 * @param combiC
	 *            combianison du Challenger
	 * @return résultat
	 */
	protected int[] afficheResultat(String combiD, String combiC) {

		int[] result = new int[2];
		int x = 0; // nb de fois où le chiffre est présent dans la combi Challenger
		int y = 0; // nb de fois où le chiffre est present dans la combi Defenseur
		int bonnePlace = 0; // nb de chiffres bien places
		int present = 0; // nb de chiffres presents dans la combo mais à la mauvaise place
		String dejaVu = ""; // archive les chiffres déjà traités

		for (int i = 0; i < combiC.length(); i++) {
			x = 0;
			y = 0;

			// recherche les chiffres à la même place dans combiC et combiD
			if (combiC.charAt(i) == combiD.charAt(i))
				bonnePlace++;

			// boucle pour trouver le nombre d'exemplaires de chaque chiffre dans combiC
			for (int j = i; j < combiC.length(); j++)
				if (dejaVu.indexOf(combiC.charAt(j)) == -1 && combiC.charAt(i) == combiC.charAt(j))
					x++;

			// recherche les chiffres présents dans les 2 combinaisons
			for (int j = 0; j < combiD.length(); j++)
				if (combiC.charAt(i) == combiD.charAt(j))
					y++;

			// permet de savoir si des doublons de combiC seraient en doublon également dans
			// combiD mais évite de faire savoir au joueur si un chiffre proposé de la
			// combinaison est en plusieurs exemplaires dans combiD
			present += (x <= y) ? x : y;

			// recense les chiffres déjà traités pour qu'ils ne soient pas traités une
			// seconde fois
			dejaVu += combiC.charAt(i);
		}
		// Le chiffre de nombre présents ne doit pas indiquer le nombre de chiffres bien
		// placés
		present -= bonnePlace;
		result[0] = bonnePlace;
		result[1] = present;

		return result;
	}

	/**
	 * demande à l'utilisateur de rentrer une combinaison du nombre de chiffres de
	 * la combinaison gagnante
	 * 
	 * @param combiD
	 * @return true si la combinaison est gagnante
	 * @see Mastermind#combinaisonValide(String)
	 */
	protected Boolean modeChallenger(String combiD) {
		String combiC;

		do {
			System.out.println("Proposez une combinaison de " + Main.nbDigits + " chiffres: ");
			combiC = sc.next();
		} while (!combinaisonValide(combiC));

		int[] result = this.afficheResultat(combiD, combiC);

		System.out.println(result[0] + " bien placés et " + result[1] + " présents.");

		if (combiC.equals(combiD)) {
			System.out.println("Vous avez gagné!");
			return true;
		} else
			return false;
	}
	
	/**Procède aux différentes étapes et appelles les méthodes pour que l'ordinateur propose des solutions */
	protected Boolean modeDefenseur(String combiD) {
		int[] t = new int[2];
		String combiC;

		if (tour == 1)
			combinaisonsPossibles(Main.nbDigits);

		int hasard = (int) (Math.random() * listeCombi.size());

		combiC = listeCombi.get(hasard);

		t = afficheResultat(combiD, combiC);

		eliminerCombinaisons(t, combiC);

		System.out.println(combiC);
		System.out.println(t[0] + " bien placés et " + t[1] + " présents. ");

		if (combiC.equals(combiD)) {
			System.out.println("L'ordinateur a gagné!");
			return true;
		} else
			return false;
	}

	/**2dite la liste des combinaisons possibles en fonction du nombre de chiffres dans la combinaison
	 * @param nbChiffres nombre de chiffres dans la combinaison */
	public static void combinaisonsPossibles(int nbChiffres) {

		for (int i = 0; i < Math.pow(10, nbChiffres); i++) {
			String combi = Integer.toString(i);

			while (combi.length() < nbChiffres)
				combi = '0' + combi;

			listeCombi.add(combi);
		}
	}
	
	/**Affiche la liste des combinaisons possibles*/
	public static void afficherCombinaisonsPossibles() {

		for (String str : listeCombi)
			System.out.print(str + "  ");
	}

	/**Passe la combinaison dans les différents filtres
	 * @param result tableau de résultat des éléments présents et bien placés
	 * @param combi combinaison proposée  */
	public static void eliminerCombinaisons(int[] result, String combi) {

		// s'il n'y a ni bien placé ni présent
		if (result[0] == 0 && result[1] == 0)
			pasPresent(combi);

		// si il y a ou moins 1 bien présent
		if (result[1] > 0)
			auMoinsUnPresent(combi, result[1]);

		// si il y a ou moins 1 bien placé
		if (result[0] > 0)
			auMoinsUnBienPlace(combi, result[0]);
	}

	/**
	 * Filtre éliminant les combinaisons qui n'ont pas au moins un des chiffres
	 * présent
	 * 
	 * @param combi
	 *            combinaison proposée
	 * @param nbPresent
	 *            nombre de chiffres présent
	 */
	public static void auMoinsUnPresent(String combi, int nbPresent) {

		for (int i = 0; i < listeCombi.size(); i++) {
			String str = listeCombi.get(i);

			int j = 0;
			int total = 0;

			do {
				if (str.indexOf(combi.charAt(j)) != -1)
					total++;
				j++;
			} while (total < nbPresent && j < combi.length());

			if (total < nbPresent) {
				listeCombi.remove(i);
				i--;
			}
		}
	}

	public static void auMoinsUnBienPlace(String combi, int nbBienPlaces) {
		for (int i = 0; i < listeCombi.size(); i++) {
			String str = listeCombi.get(i);
			int j = 0;
			int total = 0;

			do {
				if (combi.charAt(j) == str.charAt(j))
					total++;
				j++;
			} while (total < nbBienPlaces && j < combi.length());

			if (total < nbBienPlaces) {
				listeCombi.remove(i);
				i--;
			}
		}
	}

	public static void pasPresent(String combi) {

		for (int i = 0; i < listeCombi.size(); i++) {
			String str = listeCombi.get(i);
			int j = 0;

			Boolean dedans = false;

			do {
				if (str.indexOf(combi.charAt(j)) != -1)
					dedans = true;

				if (dedans) {
					listeCombi.remove(i);
					i--;
				}

				j++;
			} while (j < combi.length() && !dedans);
		}
	}

	/**
	 * Lance alternativement le mode Challenger et le mode Duel pour faire jouer
	 * l'humain et l'utilisateur à tour de rôle
	 * 
	 * @see Mastermind#modeChallenger(String)
	 * @see Mastermind#modeDefenseur(String)
	 * @param combiHumain
	 * @param combiOrdi
	 * @return true si l'un des joueurs a gané
	 */
	protected Boolean modeDuel(String combiHumain, String combiOrdi) {

		Boolean winH = modeChallenger(combiOrdi);

		if (winH) {
			System.out.println("Vous avez gagné!");
			return true;
		}

		System.out.println(("\nL'ordinateur propose: "));
		Boolean winC = modeDefenseur(combiHumain);

		if (winC) {
			System.out.println("L'ordinateur a gagné!");
			return true;
		}

		return false;
	}

}