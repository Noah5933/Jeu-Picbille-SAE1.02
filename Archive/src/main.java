import extensions.File;
import extensions.CSVFile;

class main extends Program{
    /* liste des fonctions à implémenter :
        #initplateau 
        #déplacerJoueur ( haut,bas, gauche, droite)
            #deplacementValide
        #combat
        #newjoueur ( representation : 1)
        #newvilain ( representation : 2 et +)
        Les toString ( #tableau, )
        #detecterVilain ( detecter case)
            #detecterNiveauMenace( enemie representer par 2 si menace == 1, 3 si menace == 2 etc ...)
        #scriptScenario { a developper apres alpha}
        #levelUp{ a developper si temps}
        #save ( absolument ;-; tableau + joueur)
        #histoire
        #boss ( sprite, menace 6/7, secret : 999; score superieur sinon grille vide, rajoute nom variable globale)
        equilibre ennemie
        #rajoute fichier lisable
        #rajouter boss
        #histoire qui lit l'histoire ligne par ligne tout en implementant la variable nom et les reponse du joueur
        #corriger faute orthographe
        rendre interface lisible ( upgrade mais a voir avec d'autre testeur)
        faire des tests...
        #rajouter variable globale 
        verifier pas erreur
        #reset grille + rajouter ennemi ( reapelle initGrille)


        
        # = implémanté
    */

   /* Liste des variables globales utilisé */
   String prenom ="default";
   int score = 0;
   final String saveTab = "ressources/saveTab.csv";
   final String saveJoueur = "ressources/saveJoueur.csv";
   final String medaille = "ressources/MedailleInfinie.ans";
   final String finVictoire = "ressources/FinVictoire.txt";
   final String picbille = "ressources/Picbille.txt";
   final String mange_calcul = "ressources/MangeCalcul.ans";
   final String histoire = "ressources/Histoire.txt";
   final String boo = "ressources/BooColor.ans";
   final String mini_boss = "ressources/Boss.txt";
   final String ectoplama = "ressources/Ectoplasma.txt";
   final String mauvaise_fin = "ressources/BadEnding.txt";
   final String bosstexte = "ressources/BossTexte.txt";
   final String bossfinal = "ressources/BossFinal.txt";
   final String finDefaite = "ressources/FinDefaite.txt";
   final String deus = "ressources/Deus.txt";
   final String deusTexte = "ressources/BossSecret.txt";
   final String booo = "BOOOOOOO";
   final String ecto = "ECTOPLAMAAAAAAA";
   final String mange = "LE MANGE-CALCULLLLLL";
   final String bosss = "...";
   String choixFin = "";
   int potion = 3;
   int boss1 = 2500;
   int boss2 = 5000;
   int boss3 = 7500;
   int boss_final = 10000;
   Joueur joueur =  newJoueur();
   int[][] tab = new int[10][10];
   int niveauMonde = 1;
   boolean actionAnnulee = false;




    /* initialise le plateau*/
   int[][] initplateau (int[][] tab) { 
    for (int  i = 0; i < length(tab,1); i++) {
        for (int  j = 0; j < length(tab, 2); j++) {
            tab[i][j] = 0;
            double alea = random();
            int menace = (int) (random()*2)+2+niveauMonde;
            if ( alea > 0.80)  {
                tab[i][j] = menace;
            }
        }
    }
    tab[length(tab,1)-1][length(tab,2)/2] = 1;
    return tab;
   }
/* Permet de lancer le boss */
    void lance_boss( String boss, int menace, String chemin, String nom_boss) {
        File unTexte = newFile(chemin); // texte_boss
        afficherFichier(boss); // ascii art 
        delay(1000);
        for( int i = 0; i < 2; i++) {
            println(readLine(unTexte)); // 2ere ligne boss
            delay(1000);
        }
        delay(1000);
        println(nom_boss);
        delay(2500);
        println(readLine(unTexte));
        delay(1000);
        Vilain vilain = newVilain(menace);
        combat( vilain);
    }

    void finDefaite( String chemin) {
        afficherFichier(mange_calcul);
        File unTexte = newFile(chemin);
		while(ready(unTexte)){
			println(readLine(unTexte));
            delay(2500);
		}
    }

    void bonneFin ( String chemin) {
        File unTexte = newFile(chemin);
        println(readLine(unTexte));
        delay(1000);
        println(readLine(unTexte));
        delay(1000);
        print(readLine(unTexte));
        println(prenom);
        delay(1000);
        println(readLine(unTexte));
        delay(2500);
        println(readLine(unTexte));
        delay(4000);
        println(readLine(unTexte));
        delay(4000);
        println(readLine(unTexte));
        delay(4000);
        afficherFichier(medaille);
    }

    void scriptSecret( String chemin) {
        File unTexte = newFile(chemin);
			println(readLine(unTexte));
            delay(2500);
            println(readLine(unTexte));
            delay(2500);
            println(readLine(unTexte));
            delay(2500);
    }

    /* Permet de save le tableau de jeu */
    void save_tab (int[][] tab, String nomSave) {
        String[][] tabAsString = new String[length(tab[1])+1][length(tab[2])];
        int i = 0;
        int j = 0;
         for (  i = 0; i < length(tab,1); i++) {
            for (  j = 0; j < length(tab, 2); j++) {
                tabAsString[i][j] = "" + tab[i][j];
            }
        }
        tabAsString[i][0] = "" +niveauMonde;
        tabAsString[i][1] = "" +score;
        saveCSV(tabAsString, nomSave);
    }

    /* Permet de save le type Joueur */
    void save_joueur (String nomSave) {
        String[][] joueurAsString = new String[1][4];
            joueurAsString[0][0] = "" + joueur.lvl;
            joueurAsString[0][1] = "" + joueur.pv;
            joueurAsString[0][2] = "" + joueur.xp; 
            joueurAsString[0][3] = "" + potion;
            saveCSV(joueurAsString, nomSave);
    }

    Joueur load_joueur ( String chemin) {
        CSVFile joueurAsString = loadCSV(chemin);
        int lvl = stringToInt(getCell(joueurAsString, 0, 0));
        int pv = stringToInt(getCell(joueurAsString, 0, 1));
        int xp = stringToInt(getCell(joueurAsString, 0, 2));
        potion = stringToInt(getCell(joueurAsString, 0, 3));
        return newJoueurSave(lvl, pv, xp);
    }

    int[][] load_tab ( String chemin, int[][] tab) {
        CSVFile tabAsString = loadCSV(chemin);
        int i = 0;
        int j = 0;
         for (  i = 0; i < 10; i++) {
            for (  j = 0; j < 10; j++) {
                tab[i][j] = stringToInt(getCell(tabAsString, i, j));
            }
        }
        niveauMonde = stringToInt(getCell(tabAsString, i,0));
        score = stringToInt(getCell(tabAsString, i,1));
        return tab;
    }

   /* Permet de determiner si le tableau est vide sans compter le joueur */
   boolean tableau_vide(int[][] tab){ 
    int i = 0;
    int j = 0;
    boolean trouve = false;
        while (trouve == false) {
            if ( i == length(tab,0) && j == length(tab,1)){
                return false;
            } else {
                if (tab[i][j] > 1) {
                    trouve = true;
                } else {
                    j++;
                    if (j==length(tab[2])) {
                        j=0;
                        i++;
                    }
                }
            }
        }
    return trouve;
    }

    void verifBoss(){
        if(score>=boss1 && niveauMonde==1){
            lance_boss(boo, 6, bosstexte, booo);
            niveauMonde += 1;
            actionAnnulee = true;
            initplateau(tab);
        } else if(score>=boss2 && niveauMonde==2){
            lance_boss(ectoplama, 8, bosstexte, ecto);
            niveauMonde += 1;
            actionAnnulee = true;
            initplateau(tab);
        } else if(score>=boss3 && niveauMonde==3){
            lance_boss(mini_boss, 10, bosstexte, bosss);
            niveauMonde += 1;
            actionAnnulee = true;
            initplateau(tab);
        } else if(score>=boss_final && niveauMonde==4){
            lance_boss(mange_calcul, 15, bossfinal, mange);
            niveauMonde += 1;
            actionAnnulee = true;
        }
    }

    /* Permet de lancer l'histoire avec les 2 fins differentes */
   void histoire(String chemin) { 
    File unTexte = newFile(chemin);
    println(readLine(unTexte));
    prenom = readString();
	println(readLine(unTexte));
    delay(2500);
    println(readLine(unTexte));
    boolean bon = false;
    while(bon == false) {
        choixFin = readString();
        if (equals(choixFin,"Oui") || equals(choixFin,"oui") || equals(choixFin,"non") || equals(choixFin,"Non")) {
            bon = true;
        } else {
            println("Veuillez entrer une valeur valide( Oui,oui/Non,non)");
        }
    }
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

    Joueur newJoueurSave(int lvl, int pv, int xp) {  
        Joueur joueur = new Joueur();
        joueur.lvl = lvl;
        joueur.pv_max = 100+20*joueur.lvl;
        joueur.pv = pv;
        joueur.pa = 50+20*joueur.lvl;
        joueur.xp = xp;
    return joueur;
   }


    /* créer le type joueur */
   Joueur newJoueur() {  
    Joueur joueur = new Joueur();
    joueur.lvl = 0;
    joueur.pv_max = 100;
    joueur.pv = joueur.pv_max;
    joueur.pa = 50;
    joueur.xp = 0;
    return joueur;
   }
   /* creer le type vilain */
   Vilain newVilain(int menace) {   
    Vilain vilain = new Vilain();
    vilain.pv = 100+20*menace;
    vilain.pa = 3*menace;
    vilain.menace = menace;
    vilain.xp = 10*menace;
    return vilain;
   }
   /* Permet de faite augmenter de niveau le joueur */
   void lvl_up() {  
    if (joueur.xp >= 100) {
        joueur.lvl +=1;
        joueur.pv_max = joueur.pv_max + 20;
        joueur.pv = joueur.pv_max;
        joueur.pa = joueur.pa + 20;
        joueur.xp -= 100;
        println("Bravo ! Tu as gagné un niveau.");
    } 
   }
   /* permet de déterminer les coordonnées du joueur */
   int[] coJoeur(int[][] tab) {
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
   /* permet de déplacer le joueur */
   void actionJoueur(int[][] tab, String deplace) {  
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
    if (equals(deplace,"save") || equals(deplace,"Save")) {
        save_tab(tab,saveTab);
        save_joueur( saveJoueur);
    } 
    if (equals(deplace,"load") || equals(deplace,"Load")) {
        tab = load_tab(saveTab,tab);
        joueur = load_joueur(saveJoueur);
    }
   }
   /* permet de detecter le numero de la case ( ennemie) */
   int detecterCase(int[][] tab, String deplace) {  
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
   /* Permet de supprimer un ennemie */
   void supprEnnemi(int[][]tab,String deplace){  
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
    /* permet de définir les déplacement valide */ 
    boolean deplacementValide(int[][] tab, String deplace) {
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
   /*  permet d'afficher le tableau sous forme lisible pour les humains */
     String toString(int[][] tab) {
        String tableau = "";
        for (int  i = 0; i < length(tab,1); i++) {
            for (int  j = 0; j < length(tab, 2); j++) {
                if ( tab[i][j] == 0) {
                    tableau += ".";
                }else if (tab[i][j] == 1) {
                    tableau += "J";
                } else {
                    tableau += "E";
                }
            }
            tableau += "\n";
        }
    println("score : " + score + ", monde " + niveauMonde);
    return tableau;
    }
    /* Permet d'afficher un fichier instantanément */
    void afficherFichier(String chemin){ 
		File unTexte = newFile(chemin);
		while(ready(unTexte)){
			println(readLine(unTexte));
		}
	}
    /* Permet de soigner le joueur */
    void heal() {  
        joueur.pv += 50+10*joueur.lvl;
        if ( joueur.pv > joueur.pv_max) {
            joueur.pv = joueur.pv_max;
        }
        potion -= 1;
    }
    /* permet de faire un combat entier */
    void combat( Vilain vilain) { 
        boolean enCombat=true;
        String[] operation = new String[]{"+","-","*","/"};
        int tour = 1;
        println("Vous avez rencontré un vilain de fléau : " + vilain.menace + "\n" + "Que le combat commence ! ");
        while(enCombat) {
            if (tour == 1) {
                int soin = 50+10*joueur.lvl;
                println("PV maximum/PV actuelle : " + joueur.pv_max + "/" +joueur.pv);
                delay(1000);
                println("PV adversaire : " + vilain.pv) ;
                delay(1000);
                println(" Que souhaitez-vous faire ?");
                delay(1000);
                println(" S : Soin(soigne " + soin + " PV) (nombre de potion restantes : " + potion + ")" );
                delay(1000);
                println(" Entrée : Attaque(inflige" + joueur.pa + ")");
                String choix = readString();
                if (equals(choix,"s") || equals(choix,"S")) {
                    if (potion > 0 ){
                        heal();
                        println("PV maximum/PV actuelle : " + joueur.pv_max + "/" +joueur.pv);
                        delay(1000);
                    } else {
                        println ("Le soin à échoué, vous n'avez pas de potions restantes !");
                        println("PV maximum/PV actuelle : " + joueur.pv_max + "/" +joueur.pv);
                        delay(2500);
                    }
                } else {
                    int choix_ope = (int) (random()*4);
                    int nb1 = (int) (random()*50);
                    int nb2 = (int) (random()*50);
                    int reponse = 0;
                    int reponseUser = 0;
                    if ( choix_ope == 1) {
                    if ( nb2 < nb1) {
                        println(nb1 + operation[choix_ope] + nb2 + " [Prendre la valeur inferieure] ");
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
            println("Tu as battu le vilain ! Félicitations tu peux continuer ton périple ! ");
            lvl_up();
            println("Niveau :" + joueur.lvl + "\n" + "Experience : " + joueur.xp);
            double alea = random();
            if ( alea > 0.75)  {
                potion++;
                println("Vous avez obtenu une potion !");
            }
        }
    }
    void algorithm() {
        afficherFichier(picbille);
        histoire(histoire);
        if(equals(prenom,"BASTUS")){
            joueur = load_joueur(saveJoueur);
            scriptSecret(deusTexte);
            afficherFichier(deus);
            Vilain deusBastus = newVilain(999);
            combat(deusBastus);
            println("Bastus a gagné ! Il gagne toujours !");
        }else if (equals(choixFin,"oui") || equals(choixFin,"Oui")) {
            String deplacement;
            tab = initplateau(tab);
            println(toString(tab));
            while ( joueur.pv > 0 && niveauMonde<5) {
                print("entrez une instruction valide : [Haut/Bas/Gauche/Droite/Load/Save] ");
                deplacement = readString();
                if(deplacementValide(tab,deplacement) == false){
                    println("Ceci n'est pas un déplacement valide, veuillez réessayez !");
                    delay(1000);
                }else if(deplacementValide(tab,deplacement)){
                    if(detecterCase(tab,deplacement)!=0){
                        Vilain vilain = newVilain(detecterCase(tab,deplacement));
                        combat(vilain);
                        supprEnnemi(tab,deplacement);
                        verifBoss();
                    }
                    if (actionAnnulee == false){
                     actionJoueur(tab,deplacement);
                    } else {
                        actionAnnulee = true;
                    }
                    println(toString(tab));
                }
            }
            if(niveauMonde==5 && joueur.pv > 0){
                bonneFin(finVictoire);
            } else if(niveauMonde==5 && joueur.pv <= 0){
                println("tu as perdu");
                afficherFichier(mange_calcul);
            }
        }
    }
}