import extensions.File;

class main extends Program{
    /* liste des fonctions à implémenter :
        #initplateau 
            actualiserPlateau(main)
        #déplacerJoueur ( haut,bas, gauche, droite)
            #deplacementValide
        #combat
        #newjoueur ( representation : 1)
        #newvilain ( representation : 2 et +)
        Les 800 toString ( #tableau, )
        #detecterVilain ( detecter case)
            #detecterNiveauMenace( enemie representer oar 2 si menace == 1, 3 si menace == 2 etc ...)
        #scriptScenario { a developper apres alpha}
        #levelUp{ a developper si temps}
        save ( absolument ;-; tableau + joueur)
        #histoire
        boss ( sprite, menace 6/7, secret : 99999; score superieur sinon grille vide, rajoute nom variable globale)
        equilibre ennemie
        #rajoute fichier lisable
        rajouter boss
        #histoire qui lit l'histoire ligne par ligne tout en implementant la variable nom et les reponse du joueur
        corriger faute orthographe
        rendre interface lisible
        faire des tests
        rajouter variable globale 
        verifier pas erreur


        
        # = implémanté
    */
   String prenom ="";
   int score = 0;
   final String picbille = "ressources/Picbille.txt";
   final String mange_calcul = "ressources/GoudronColor.ans";
   final String histoire = "ressources/Histoire.txt";
   final String mauvaise_fin = "ressources/BadEnding.txt";
   String choixFin = "";
   int potion = 3;




   int[][] initplateau (int[][] tab) { //initialise le plateau ( a améliorer)
    for (int  i = 0; i < length(tab,1); i++) {
        for (int  j = 0; j < length(tab, 2); j++) {
            tab[i][j] = 0;
            double alea = random();
            int menace = (int) (random()*3)+2;
            if ( alea > 0.80)  {
                tab[i][j] = menace;
            }
        }
    }
    tab[length(tab,1)-1][length(tab,2)/2] = 1;
    return tab;
   }
   void histoire(String chemin) { //Permet de lancer l'histoire avec les 2 fins differentes
    File unTexte = newFile(chemin);
    println(readLine(unTexte));
    prenom = readString();
	println(readLine(unTexte));
    delay(2500);
    println(readLine(unTexte));
    choixFin = readString();
    if (equals(choixFin,"non") || equals(choixFin,"Non")) {
        afficherFichier(mauvaise_fin);
    } else {
        println(readLine(unTexte));
        String tmp = readString();
        println(readLine(unTexte));
        tmp = readString();
        println(readLine(unTexte));
        delay(2500);
        println(readLine(unTexte));
        delay(5000);
        }
	}
   Joueur newJoueur() { // créer le type joueur
    Joueur joueur = new Joueur();
    joueur.lvl = 0;
    joueur.pv_max = 100+15*joueur.lvl;
    joueur.pv = joueur.pv_max;
    joueur.pa = 200+2*joueur.lvl;
    joueur.xp = 0;
    return joueur;
   }
   Vilain newVilain(int menace) { //  creer le type vilain
    Vilain vilain = new Vilain();
    vilain.pv = 100+30*menace;
    vilain.pa = 10+5*menace;
    vilain.menace = menace;
    vilain.xp = 10*menace;
    return vilain;
   }
   void lvl_up(Joueur joueur) { // Permt de faite augmenter de niveau le joueur
    if (joueur.xp >= 100) {
        joueur.lvl +=1;
        joueur.xp -= 100;
        println("Bravo ! Tu as gagné un niveau.");
    } 
   }
   int[] coJoeur(int[][] tab) { // permet de déterminer les coordonnées du joueur
    int i = 0;
    int j = 0;
    boolean trouve = false;
    while (trouve == false) {
        if (tab[i][j] == 1) {
            trouve = true;
        } else {
            j++;
            if (j==length(tab[2])) {
                j=0;
                i++;
            }
        }
    }
    int[] co = new int[]{i,j};
    return co;
   }
   void deplacerJoueur(int[][] tab, String deplace) { // permet de déplacer le joueur
    int[] co=coJoeur(tab);
    int i=co[0];
    int j=co[1];
    if (equals(deplace,"haut") || equals(deplace,"Haut")) {
        tab[i-1][j] = 1;
        tab[i][j] = 0;
    }
    if (equals(deplace,"bas") || equals(deplace,"Bas")) {
        tab[i+1][j] = 1;
        tab[i][j] = 0;
   }
    if (equals(deplace,"gauche") || equals(deplace,"Gauche")) {
        tab[i][j-1] = 1;
        tab[i][j] = 0;
   }
    if (equals(deplace,"droite") || equals(deplace,"Droite")) {
        tab[i][j+1] = 1;
        tab[i][j] = 0;
   }
   }
   int detecterCase(int[][] tab, String deplace) { // permet de detecter le numero de la case ( ennemie)
    int[] co = coJoeur(tab);
    int  i = co[0];
    int j = co[1];
    int chiffre = 0;
    if (equals(deplace,"haut") || equals(deplace,"Haut")) {
        chiffre = tab[i-1][j];
   }
    if (equals(deplace,"bas") || equals(deplace,"Bas")) {
        chiffre =  tab[i+1][j];
   }
   if (equals(deplace,"droite") || equals(deplace,"Droite")) {
        chiffre =  tab[i][j+1];
   }
   if (equals(deplace,"gauche") || equals(deplace,"Gauche")) {
        chiffre =  tab[i][j-1];
   }
   return chiffre;
   }
   void supprEnnemi(int[][]tab,String deplace){ // Permet de supprimer un ennemie
        int[] co = coJoeur(tab);
        int  i = co[0];
        int j = co[1];
        if (equals(deplace,"haut") || equals(deplace,"Haut")) {
            tab[i-1][j]=0;
        }
        if (equals(deplace,"bas") || equals(deplace,"Bas")) {
            tab[i+1][j]=0;
        }
        if (equals(deplace,"droite") || equals(deplace,"Droite")) {
            tab[i][j+1]=0;
        }
        if (equals(deplace,"gauche") || equals(deplace,"Gauche")) {
            tab[i][j-1]=0;
        }
   }
    boolean deplacementValide(int[][] tab, String deplace) { // permet de définir les déplacement valide
            int[] co = coJoeur(tab);
            int  i = co[0];
            int j = co[1];
            if ((equals(deplace,"haut") || equals(deplace,"Haut")) && i == 0) {
                return false;
            }
            if ((equals(deplace,"bas") || equals(deplace,"Bas")) && i == length(tab,1)-1) {
                return false;
            }
            if ((equals(deplace,"gauche") || equals(deplace,"Gauche")) && j == 0) {
                return false;
            }
            if ((equals(deplace,"droite") || equals(deplace,"Droite")) && j == length(tab,2)-1) {
                return false;
            }
            return true;
        }
    String toString(int[][] tab) { // permet d'afficher le tableau sous forme lisible pour les humains
        String tableau = "";
        for (int  i = 0; i < length(tab,1); i++) {
            for (int  j = 0; j < length(tab, 2); j++) {
                if ( tab[i][j] == 0) {
                    tableau += ".";
                }else if (tab[i][j] == 1) {
                    tableau += "J";( si temps mais giga flemme)
                } else {
                    tableau += "E";
                }
            }
            tableau += "\n";
        }
    println("score : " + score );
    return tableau;
    }
    void afficherFichier(String chemin){ // Permet d'afficher un fichier instantanément
		File unTexte = newFile(chemin);
		while(ready(unTexte)){
			println(readLine(unTexte));
		}
	}
    void heal(Joueur joueur) {
        joueur.pv += 50+10*joueur.lvl;
        if ( joueur.pv > joueur.pv_max) {
            joueur.pv = joueur.pv_max;
        }
        potion -= 1;
    }
    void combat(Joueur joueur, Vilain vilain) { // permet de faire un combat entier
        boolean enCombat=true;
        String[] operation = new String[]{"+","-","*","/"};
        int tour = 1;
        println("Vous avez rencontré un vilain de fléau : " + vilain.menace + "\n" + "Que le combat commence ! ");
        while(enCombat) {
            if (tour == 1) {
                int soin = 50+10*joueur.lvl;
                println("PV maximum/PV actuelle : " + joueur.pv_max + "/" +joueur.pv);
                println("PV adversaire : " + vilain.pv) ;
                println(" Que souhaitez-vous faire ?");
                println(" S : Soin(soigne " + soin + " PV)");
                println(" Entrée : Attaque(inflige" + joueur.pa +  ")");
                String choix = readString();
                if (equals(choix,"s") || equals(choix,"S")) {
                    if (potion > 0 ){
                        heal(joueur);
                        println("PV maximum/PV actuelle : " + joueur.pv_max + "/" +joueur.pv);
                    } else {
                        println ("Le soin à échoué, vous n'avez pas de potions restantes !");
                        println("PV maximum/PV actuelle : " + joueur.pv_max + "/" +joueur.pv);
                    }
                } else {
                    int choix_ope = (int) (random()*4);
                    int nb1 = (int) (random()*50);
                    int nb2 = (int) (random()*50);
                    int reponse = 0;
                    int reponseUser = 0;
                    if ( choix_ope == 1) {
                    if ( nb2 < nb1) {
                        println(nb1 + operation[choix_ope] + nb2);
                    } else {
                        println(nb2 + operation[choix_ope] + nb1);
                        }
                    } else {
                        println(nb1 + operation[choix_ope] + nb2);
                    }
                    print("quelle est la réponse ? ");
                    reponseUser=readInt();
                    if ( choix_ope == 0) {
                        reponse = nb1+nb2;
                    } else if ( choix_ope == 1) {
                        if ( nb2 < nb1) {
                            reponse = nb1-nb2;
                        } else {
                        reponse = nb2-nb1;
                        }
                    }else if (choix_ope == 2) {
                        reponse = nb1*nb2;
                    } else {
                        reponse = nb1/nb2;
                    }
                    println("La reponse est : " + reponse);
                    if(reponse==reponseUser){
                        println("attaque réussie !!");
                        vilain.pv=vilain.pv-joueur.pa;
                    }
                }
                tour = 2;
            } else {
                joueur.pv -= vilain.pa;
                tour = 1;
            }
            if(joueur.pv<=0 || vilain.pv<=0){
                enCombat = false;
            }
        }
        if(joueur.pv > 0 ) {
            joueur.xp += vilain.xp;
            score += 100*vilain.menace;
            println("Tu as battu le vilain ! Félicitation tu peux continuer ton périple ! ");
            lvl_up(joueur);
            println("Niveau :" + joueur.lvl + "\n" + "Experience : " + joueur.xp);
            double alea = random();
            if ( alea > 0.75)  {
                potion++;
                println("Vous avez obtenu une potion !");
            }
        }
    }
    void algorithm() {
        // a terminer

        afficherFichier(picbille);
        histoire(histoire);
        if (equals(choixFin,"oui") || equals(choixFin,"Oui")) {
            String deplacement;
            int[][] tab = new int[10][10];
            tab = initplateau(tab);
            println(toString(tab));
            Joueur joueur =  newJoueur();
            while ( joueur.pv > 0) {
                print("entrez une direction valide : [Haut/Bas/Gauche/Droite] ");
                deplacement = readString();
                if(deplacementValide(tab,deplacement)){
                    if(detecterCase(tab,deplacement)!=0){
                        Vilain vilain = newVilain(detecterCase(tab,deplacement));
                        combat(joueur,vilain);
                        supprEnnemi(tab,deplacement);
                    }
                    deplacerJoueur(tab,deplacement);
                    println(toString(tab));
                }
            }
            println("tu as perdu");
            afficherFichier(mange_calcul);
        }
    }
}