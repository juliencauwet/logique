package logique;
import java.util.ArrayList;

import java.util.Scanner;

public class Recherche extends Game {
	
	public static int tour = 0;
	public static ArrayList <String> outcome = new ArrayList <String> ();
	public static ArrayList <String> listePropositions = new ArrayList <String> ();
	
	public Recherche() {
		tour ++;
	}
	
	
	Scanner sc = new Scanner(System.in);
	

	String afficheResultat(String combiDefenseur, String combiChallenger) {
		
		String result = "";
		
		for (int i = 0 ; i < combiDefenseur.length() ; i++)
			if (combiChallenger.charAt(i) == combiDefenseur.charAt(i))
				result += "=";
			else if (combiChallenger.charAt(i) < combiDefenseur.charAt(i))
				result += "+";
			else 
				result += "-";		
		
		return result;
	}
	
	protected Boolean modeDefenseur(String combiD) {
		
		String combiC = deduireCombinaison();
		
		System.out.println (combiC);
		System.out.println(afficheResultat(combiD, combiC));
		outcome.add(afficheResultat(combiD, combiC));

		if (combiC.equals(combiD)) {
			System.out.println("L'ordinateur a gagné!");
			return true;
		}else
			return false;	
		
	}


	protected Boolean modeChallenger(String combiD) {
		
		System.out.println("Proposez une combinaison: ");
		String combiC = sc.nextLine();
		System.out.println(this.afficheResultat(combiD, combiC));
		
		if (combiC.equals(combiD)) 
			return true;
		else
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
		
		if(winC){
			System.out.println("L'ordinateur a gagné!");
			return true;
		}		
			
		return false;
	}
			
	
	
	protected String deduireCombinaison() {
		String combi = "";
		String result;
		
		if (tour == 1) {
			combi= combinaisonAleatoire(Main.nbDigits);
		}else {
			result = outcome.get(tour-2);
			
			for (int i = 0; i < Main.nbDigits; i++) {
				
				if (result.charAt(i) == '=') {
					combi += listePropositions.get(tour-2).charAt(i);
				}else
					combi += chiffreCle(i, result.charAt(i));					
			}	
		}
	
		listePropositions.add(combi);
		return combi;
	}
	
	protected String chiffreCle(int index, char indicateur) {
		
		int avantDernierChiffre = (indicateur == '+')? 10 : 0;
		int dernierChiffre = Character.getNumericValue(listePropositions.get(tour-2).charAt(index));	
		char dernierSigne = outcome.get(tour-2).charAt(index);
		char avantDernierSigne = indicateur;		
		String chiffre;
		
		if (tour > 2 ) {
			avantDernierChiffre = Character.getNumericValue(listePropositions.get(tour-3).charAt(index));
			avantDernierSigne = outcome.get(tour-3).charAt(index);
		}	
		
		if (avantDernierSigne == '+' && dernierSigne =='+' )
			chiffre = Integer.toString(dernierChiffre + (9 - dernierChiffre)/2);
		else if (avantDernierSigne == '-' && dernierSigne == '-')
			chiffre = Integer.toString(dernierChiffre/2);
		else
			chiffre = Integer.toString((dernierChiffre + avantDernierChiffre)/2);
		
		return chiffre;
	}
	
	

}
