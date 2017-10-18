package logique;

import java.util.ArrayList;

import java.util.Scanner;

/** Classe qui contien toutes les méthodes nécessaires au jeu Recherche */
public class Recherche extends Game {

	public static int tour = 0;
	public static ArrayList<String> outcome = new ArrayList<String>();
	public static ArrayList<String> listePropositions = new ArrayList<String>();

	public Recherche() {
		tour++;
	}

	Scanner sc = new Scanner(System.in);

	/**
	 * Affiche le résultat en fonction de la combinaison du joueur par rapport à la
	 * combinaison du défenseur
	 * 
	 * @param combiDefenseur
	 * @param combiChallenger
	 * @return le résultat en indicateurs + ou - pour chaque chiffre de la
	 *         combinaison
	 */
	String afficheResultat(String combiDefenseur, String combiChallenger) {

		String result = "";

		for (int i = 0; i < combiDefenseur.length(); i++)
			if (combiChallenger.charAt(i) == combiDefenseur.charAt(i))
				result += "=";
			else if (combiChallenger.charAt(i) < combiDefenseur.charAt(i))
				result += "+";
			else
				result += "-";

		return result;
	}

	/**
	 * Appelle deduireCombinaison pour obtenir une combinaison intelligente Affiche
	 * la combinaison Affiche le résultat
	 * 
	 * @param combiD
	 *            combinaison gagnante
	 * @return true si la combinaison est gagnante
	 */
	protected Boolean modeDefenseur(String combiD) {

		String combiC = deduireCombinaison();

		System.out.println(combiC);
		System.out.println(afficheResultat(combiD, combiC));
		outcome.add(afficheResultat(combiD, combiC));

		if (combiC.equals(combiD)) {
			System.out.println("L'ordinateur a gagné!");
			return true;
		} else
			return false;

	}

	/**
	 * demande à l'utilisateur de rentrer une combinaison du nombre de chiffres de
	 * la combinaison gagnante
	 * 
	 * @param combiD
	 * @return true si la combinaison est gagnante
	 * @see Recherche#combinaisonValide(String)
	 */
	protected Boolean modeChallenger(String combiD) {
		String combiC;

		do {
			System.out.println("Proposez une combinaison de " + Main.nbDigits + " chiffres: ");
			combiC = sc.next();
		} while (!combinaisonValide(combiC));

		System.out.println(this.afficheResultat(combiD, combiC));

		if (combiC.equals(combiD)) {
			// System.out.println("Vous avez gagné!");
			return true;
		} else
			return false;
	}

	/**
	 * Lance alternativement le mode Challenger et le mode Duel pour faire jouer
	 * l'humain et l'utilisateur à tour de rôle
	 * 
	 * @see Recherche#modeChallenger(String)
	 * @see Recherche#modeDefenseur(String)
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

	/**
	 * Propose la combinaison qui se rapproche le plus de la solution en utilisant
	 * pour chaque chiffre Archive la liste des propositions dans listePropositions
	 * 
	 * @see Recherche#chiffreCle(int, char)
	 * @return la combinaison composée de l'ensemble des chiffres clés
	 */
	protected String deduireCombinaison() {
		String combi = "";
		String result;

		if (tour == 1) {
			combi = combinaisonAleatoire(Main.nbDigits);
		} else {
			result = outcome.get(tour - 2);

			for (int i = 0; i < Main.nbDigits; i++) {

				if (result.charAt(i) == '=') {
					combi += listePropositions.get(tour - 2).charAt(i);
				} else
					combi += chiffreCle(i, result.charAt(i));
			}
		}

		listePropositions.add(combi);
		return combi;
	}

	/**
	 * Commence uniquement au 2ème tour. En fonction des propositions précédentes,
	 * recherche le chiffre qui se rapproche le plus de la solution en le déduisant
	 * des résultats obtenus.
	 * 
	 * @param index
	 *            postion du chiffre dans la combinaison
	 * @param indicateur
	 *            '+' ou '-' du résultat du tour précédent
	 * @return chiffre
	 */
	protected String chiffreCle(int index, char indicateur) {

		int avantDernierChiffre = (indicateur == '+') ? 10 : 0; // chiffre proposé dans la combinaison n-2
		// 0 ou 10 au 2ème tour en fonction de l'indicateur
		int dernierChiffre = Character.getNumericValue(listePropositions.get(tour - 2).charAt(index));
		char dernierSigne = outcome.get(tour - 2).charAt(index);
		char avantDernierSigne = indicateur;
		String chiffre;

		if (tour > 2) {
			avantDernierChiffre = Character.getNumericValue(listePropositions.get(tour - 3).charAt(index));
			avantDernierSigne = outcome.get(tour - 3).charAt(index);
		}

		if (avantDernierSigne == '+' && dernierSigne == '+')
			chiffre = Integer.toString(dernierChiffre + (10 - dernierChiffre) / 2);
		else if (avantDernierSigne == '-' && dernierSigne == '-')
			chiffre = Integer.toString(dernierChiffre / 2);
		else
			chiffre = Integer.toString((dernierChiffre + avantDernierChiffre) / 2);

		return chiffre;
	}

}
