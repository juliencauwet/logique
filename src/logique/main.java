package logique;

import java.util.Scanner;
import java.util.Arrays;

public class main {

	public static void main(String[] args) {
		//*******DECLARATION VARIABLES DE PARAMETRAGES*******
		int nbDigits=4; //combien de chiffres/ couleurs dans la combinaison
		boolean developpeur=false; //mode développeur
		int nbChances=10;
		
		// ****DECLARATION VARIABLES********
		int choix[]=new int[2]; //choix du jeu et du mode
		boolean win=false; // si la combinaison est gagnante
		Scanner sc = new Scanner(System.in);
		int combiJoueur; //combinaison du joueur
		int tCombiJoueur[] = new int[nbDigits]; // conversion combinaison joueur en tableau
		int[]tCombiOrdi = new int[nbDigits]; //tableau de la combinaison du tableau
		int nbTours=1; 
		char recommencer; // si le joueur souhaite recommencer au menu principal
		
		
	//*************LANCEMENT MENU: CHOIX DU JEU PUIS CHOIX DU MODE***************
		do {
			choix=global.menu(); 
	//*************MODE CHALLENGER***************		
			if (choix[1]==1) {
					tCombiOrdi = global.genereCombi(nbDigits); // si le mode est Challnger, alors l'ordinateur génere une combinaison aléatoire
					if (developpeur == true) {
						for (int x : tCombiOrdi)
							System.out.print(x);	
					}
					
					do{
						System.out.println("\nessai n°"+nbTours+" de combinaison: (composée uniquement de " +nbDigits+ " chiffres)");
						combiJoueur=sc.nextInt();
						nbTours++;
						tCombiJoueur=global.tabellisation(combiJoueur,nbDigits);			
			
						if(choix[0]==1)					//**********RECHERCHE +/-********					
							System.out.println(jeux.rechercheModeChallenger(nbDigits,tCombiJoueur,tCombiOrdi));
						else								//**********MASTERMIND***********
							System.out.println(jeux.mastermindModeChallenger(nbDigits,tCombiJoueur,tCombiOrdi));							
												
						win=java.util.Arrays.equals(tCombiOrdi, tCombiJoueur);// si les 2 tableaux sont égaux alors le joueur a gagné
					} while(!win && nbTours <= nbChances);
					
			
					if (win) 
						System.out.println("\nVous avez gagné!!!");
					else {
						System.out.println("\nVous avez perdu! La solution était: ");
						for (int x : tCombiOrdi)
							System.out.print(x);	
					}
					nbTours=1;
	//************MODE DEFENSEUR****************				
			}else if (choix[1]==2) {
				if(choix[0]==1)
					jeux.rechercheModeDefenseur(nbDigits,nbChances);
				else if(choix[0]==2) {
					
				}else if(choix[0]==3) {
					
				}
			}
	//************FIN MODE DEFENSEUR************		
				
			System.out.println("\nSouhaitez-vous recommencer?(O/N);");
			recommencer=sc.nextLine().charAt(0);
		 }while(recommencer=='O');  
		
		System.out.println("Merci et à bientôt"); 
	}
	
}	