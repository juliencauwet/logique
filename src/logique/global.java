package logique;

import java.util.Scanner;

public class global {
	public static int[] menu(){
		int j = 0;
		int m = 0;
		int option[]= new int[2];
		Scanner rep = new Scanner(System.in);
		
		do{
			System.out.println("Bonjour, à quoi souhaitez vous jouer?");
			System.out.println("1-Recherche +/-");
			System.out.println("2-Mastermind");
			j=rep.nextInt(); //Récupération du choix de jeu

			System.out.println("Dans quel mode souhaitez-vous jouer?");
			System.out.println("1-Mode Challenger: vous devez trouver la combinaison secrète de l'ordinateur");
			System.out.println("2-Mode Défenseur: c'est à l'ordinateur de trouver votre combinaison secrète");
			System.out.println("3-Mode Duel: l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné");
			m=rep.nextInt(); //Récupération du choix de mode

			if(j!=1 && j!=2)
				System.out.println("Le jeu choisi n'existe pas!");
			if(m!=1 && m!=2 && m!=3)
				System.out.println("Le mode choisi n'existe pas!");}while((j!=1 && j!=2) || (m!=1 && m!=2 && m!=3));

			System.out.println("Vous avez choisi le " + (j==1? "jeu de la Recherche +/- ": "Mastermind ") + "en mode "+(m==1?"Challenger":m==2?"Défenseur":"Duel"));
			option[0]=j;
			option[1]=m;
			return option;
	}
	
	//genereCombi: méthode qui génère une combinaison aléatoire dans un tableau:
	public static int[] genereCombi (int nbChiffres){

		int i;
		int[] combi= new int[nbChiffres];
		for (i=0;i<nbChiffres;i++)
		combi[i]=(int)(Math.random()*10);
					
		return combi;	
	}
	
	//solutionRecherche: propose une combinaison n en fonction de la combinaison n-1 et du résultat obtenu
	public static int[] solutionRecherche(int nbChiffres, String resultat, int combiPrecedente[], int combiAvant[], int nbT){
		int code[]=new int[nbChiffres];	
		for(int i=0; i<nbChiffres;i++) {
			System.out.println("combiAvant: "+combiAvant[i]+" combiPre: "+combiPrecedente[i]);
			if (resultat.charAt(i)=='+') {				
				if(nbT==1)
					combiAvant[i]=9;
				System.out.println("*combiAvant: "+combiAvant[i]+" combiPre: "+combiPrecedente[i]);
				code[i]=combiPrecedente[i]+(int)((combiAvant[i]-combiPrecedente[i])/2);
			}else if(resultat.charAt(i)=='-') {
				if(nbT==1)
					combiAvant[i]=0;
				System.out.println("*combiAvant: "+combiAvant[i]+" combiPre: "+combiPrecedente[i]);
				code[i]=(int)((combiAvant[i]+combiPrecedente[i])/2);
			}else 
				code[i]=combiPrecedente[i];
			System.out.println("**combiAvant: "+combiAvant[i]+" combiPre: "+combiPrecedente[i]);
			System.out.println("code[i]: "+code[i]);
		}
		return code;
	}
	
	// Tabellisation: méthode qui convertit la combinaison du joueur(int) en tableau (nombre de milliers,centaines,dizaines...)
	public static int[] tabellisation(int combo, int nbChiffres) { 
		int i=0;
		int div; // dividende
		int reste=combo; //reste 
		int x[]=new int[nbChiffres];
		while(nbChiffres>1) {
		div=(int)(combo/Math.pow(10, nbChiffres-1)); 
		reste=(int) (combo%Math.pow(10, nbChiffres-1)); 
		combo=combo-div*(int)Math.pow(10, nbChiffres-1); 
		x[i]=div;
		nbChiffres--;
		i++;			
		}
		x[i]=reste;
		
		return x;
	}

	//propose à l'utisateur de générer une combi avec genereCombi ou de la composer lui même
	public static int[] choixCombi(int nbChiffres){
		char auto;
		int combi;
		int tCombi[]=new int[nbChiffres];
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez-vous generer une combinaison automatiquement?: (O/N)");
		auto=sc.nextLine().charAt(0);
		
		if(auto=='O')
			tCombi=genereCombi(nbChiffres);
		else {
			System.out.println("Veuillez entrer la combinaison à "+nbChiffres + " chiffres: ");
			combi=sc.nextInt();
			tCombi=tabellisation(combi,nbChiffres);
		}
		
		return tCombi;
	}

	//Convertit un chiffre en couleur(caractère)
	public static char deChiffreACouleur(int chiffre) {
		char couleur='W';
		switch(chiffre) {
		case 0 : couleur='B'; //Blanc
		break;
		case 1 : couleur='N'; //Noir
		break;
		case 2 : couleur='V'; //Vert
		break;
		case 3 : couleur='J'; //Jaune
		break;
		case 4 : couleur='R'; //Rouge
		break;
		case 5 : couleur='O'; //Orange
		break;
		case 6 : couleur='M'; //Marron
		break;
		case 7 : couleur='G'; //Gris
		break;
		case 8 : couleur='K'; //Kaki
		break;
		case 9 : couleur='D'; //Doré
		break;
		
		}
		return couleur;
	}
}
