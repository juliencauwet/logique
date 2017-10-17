package logique;

import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Mastermind extends Game {

	private static final Logger logger = Logger.getLogger(Mastermind.class);

	public static int tour = 0;
	public static int chiffresTrouves = 0;
	public static ArrayList<Integer> outcome = new ArrayList<Integer>();
	public static ArrayList<String> listePropositions = new ArrayList<String>();

	public Mastermind() {
		tour++;
	}

	Scanner sc = new Scanner(System.in);

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

	protected Boolean modeChallenger(String combiD) {
		String combiC;

		do {
			System.out.println("Proposez une combinaison de " + Main.nbDigits + " chiffres: ");
			combiC = sc.nextLine();
		} while (!combinaisonValide(combiC));

		int[] result = this.afficheResultat(combiD, combiC);

		System.out.println(result[0] + " bien placés et " + result[1] + " présents.");

		if (combiC.equals(combiD)) {
			System.out.println("Vous avez gagné!");
			return true;
		} else
			return false;
	}

	protected Boolean modeDefenseur(String combiD) {
		String combiC = "";
		int[] resultat = new int[2];

		// Tant que le nombre de chiffres de la combinaison ne sont pas trouvés
		while (chiffresTrouves < Main.nbDigits) {

			// si 1er tour, la combinaison est 0000 et sinon elle s'incrémente de 1
			if (tour == 1)
				combiC = "0000";
			else {
				for (int i = 0; i < Main.nbDigits; i++)
					combiC += (char) (tour - 1 + '0');
			}

			// archivage et affichage de la combinaison
			listePropositions.add(combiC);
			System.out.println(combiC);

			resultat = this.afficheResultat(combiD, combiC);

			// si il y a des chiffres à la bonne place, on les ajoute à chiffresTrouves
			if (resultat[0] > 0) {
				chiffresTrouves += resultat[0];
				// et on les assemble avec les autres chiffres gagnants
				for (int i = 0; i < resultat[0]; i++)
					outcome.add(tour - 1);
			}

			System.out.println(resultat[0] + " bien placés et " + resultat[1] + " présents.");
			System.out.println(chiffresTrouves);
			return false;
		}

		// quand tous les chiffres ont été trouvés, on en fait une combinaison pour
		// connaitre le résultat
		for (int c : outcome)
			combiC += Integer.toString(c);

		System.out.println(combiC);
		resultat = this.afficheResultat(combiD, combiC);
		System.out.println(resultat[0] + " bien placés et " + resultat[1] + " présents.");

		if (combiC.equals(combiD)) {
			System.out.println("L'ordinateur a gagné!");
			return true;
		} else
			return false;
	}

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
