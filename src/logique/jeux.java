package logique;

public class jeux {
	public static String rechercheModeChallenger(int nbChiffres,int[] combJ, int[]combO){
		String result="";
		for(int i=0;i<nbChiffres;i++) {
			if (combJ[i]==combO[i]) {
				result+="=";
			}else if(combJ[i]<combO[i]){
				result+="+";
			}else {
				result+="-";
			}
		}
		return result;
	}
	
	public static String mastermindModeChallenger(int nbChiffres,int[] combJ, int[]combO){
		String strCombi="";	//conversion de la combi en String pour utiliser indexOf
		String strDejaVu=""; //marqueur
		int bonnePlace=0;	//nb de chiffres bien places
		int present=0;		//nb de chiffres presents dans la combo mais à la mauvaise place
		String result="";
		for (int i=0;i<nbChiffres;i++) {
			strCombi+=Integer.toString(combO[i]);					
		}
		System.out.print(strCombi);
		System.out.print("\n");
		for(int j=0;j<nbChiffres;j++) {
			int test=strCombi.indexOf(Integer.toString(combJ[j]));	//vérifie si le chiffre se trouve dans la combinaison de l'ordinateur
			if(test!=-1 && strDejaVu.indexOf(test)==-1) { 			//si le chiffre se trouve dans la combi de l'ordi et si il n'a pas déjà été comptabilisé
				strDejaVu+=test; 									//on archive l'index ou le chiffre a été trouvé dans tCombiOrdi

				if (test==j) 
					bonnePlace++;
				else
					present++;
			}
		}	
		result = bonnePlace+ " bien placé(s) et " + present+ " présent(s)";
		return result;
	}
	
	public static void rechercheModeDefenseur(int nbChiffres,int nbCh){
		int nbTours=1;
		int combiSecrete[]= new int[nbChiffres];
		int combO[]= new int[nbChiffres];
		int combA[]= new int[nbChiffres];
		combiSecrete=global.choixCombi(nbChiffres);
		boolean winTest=false;
		String result="";
		
		combO=global.genereCombi(nbChiffres);
		
		do{
			System.out.println("Proposition de l'ordinateur n°"+ nbTours+": ");
	
		for(int x: combO)
			System.out.print(x);
		System.out.print("\n");
		result=jeux.rechercheModeChallenger(nbChiffres,combO,combiSecrete);
		System.out.println(result);
		winTest=java.util.Arrays.equals(combiSecrete, combO);
		combA=combO;
		combO=global.solutionRecherche(nbChiffres,result,combO,combA, nbTours);
		nbTours++;}while(!winTest && nbTours<=nbCh );
	}
}
