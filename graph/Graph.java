package graph;

import java.util.ArrayList;

/**
 * Created by anthonybertrant on 10/11/2016.
 */

public class Graph {

    private ArrayList<Sommet> listeSommets = new ArrayList<>();
    private ArrayList<Arete> listeAretes = new ArrayList<>();

    public void addSommet(Sommet sommet){
        listeSommets.add(sommet);
    }

    public void addArete(int poids, Sommet sommet1, Sommet sommet2){
        listeAretes.add(new Arete(poids, sommet1, sommet2));
    }

    public Arete trouverLaPlusPetiteArete(ArrayList<Arete> aretes){
        Arete areteResult = aretes.get(0);
        for(int i = 1; i < aretes.size(); ++i){
            if(areteResult.getPoids() > aretes.get(i).getPoids()){
                areteResult = aretes.get(i);
            }
        }
        return areteResult;
    }

    public boolean controleCycle(Sommet sommet1, Sommet sommetRecherche, ArrayList<Arete> listeArete){
        //à la premiere iteration, sommet1 est un des deux sommets de l'arete, et sommetRecherche est l'autre sommet de cette arete
            if(sommet1 == sommetRecherche) {
                System.out.println("IL Y A UN CYCLE !\n");
                return true;
            }
            ArrayList<Arete> listeAreteNonVerif = new ArrayList<>();
            listeAreteNonVerif.addAll(listeArete);
            for(int index = 0 ; index < listeArete.size() ; index++){
                if(listeArete.get(index).getSommet1() == sommet1){
                    listeAreteNonVerif.remove(listeArete.get(index));
                    if (controleCycle(listeArete.get(index).getSommet2(), sommetRecherche, listeAreteNonVerif)) {
                        return true;
                    }
                }
                else if(listeArete.get(index).getSommet2() == sommet1){
                    listeAreteNonVerif.remove(listeArete.get(index));
                    if(controleCycle(listeArete.get(index).getSommet1(), sommetRecherche, listeAreteNonVerif)){
                        return true;
                    }
                }
            }
            System.out.println("Il n'y a pas de cycle\n");
            return false;
        }

    public void afficherSommets(){
        for(int i = 0; i < listeSommets.size(); i++){
            System.out.println("sommet " + listeSommets.get(i).toString());
        }
    }

    public void afficherAretes(){
        for(int i = 0; i < listeAretes.size(); i++){
            System.out.println(listeAretes.get(i).toString());
        }
    }

    /*---------- FONCTIONS DE TRI ----------*/

    public ArrayList<Arete> algoDeKruskal(){
        ArrayList<Arete> aretes = this.listeAretes;
        ArrayList<Arete> aretesResultat = new ArrayList<>();

        Arete areteTest;

        int i = 0;
        while(i < listeSommets.size()-1){ //Le nombre d'arete doit etre de nbSommet - 1
            areteTest = trouverLaPlusPetiteArete(aretes);

            if(!controleCycle(areteTest.getSommet1(),areteTest.getSommet2(),aretesResultat)){
                aretesResultat.add(areteTest); //On ajoute la plus petite arete a notre lot
                i += 1;
            }
            aretes.remove(areteTest); //On la supprime des aretes disponibles
        }
        return aretesResultat;
    }

    public ArrayList<Arete> pushArete(Sommet sommet, ArrayList<Arete> aretes){

        for(int i = 0; i < sommet.getListAretes().size(); i++){
            aretes.add(sommet.getListAretes().get(i));
        }

        return aretes;
    }

    public ArrayList<Arete> algoDePrim(){

        //TODO prim

        ArrayList<Arete> aretesResultat = new ArrayList<>(); //contient les aretes du graph minimal
        ArrayList<Arete> areteDispo = new ArrayList<>(); //Aretes disponible a la selection a un instant t
        ArrayList<Sommet> sommets = new ArrayList<>(); //contient les sommets du graph que l'on a explorer

        sommets.add(listeSommets.get(4)); //selection du premier sommet

        int i = 0;

        while (aretesResultat.size() != listeSommets.size() - 1){

            pushArete(sommets.get(i), areteDispo); //ajoute la liste des aretes d'un sommet dans notre tableau
            areteDispo.removeAll(aretesResultat); //on supprime les chemin deja selectionner comme resultat

            Arete areteSelect = trouverLaPlusPetiteArete(areteDispo);
            if(!controleCycle(areteSelect.getSommet1(), areteSelect.getSommet2(), aretesResultat)){ //S'il n'y a pas de cycle
                aretesResultat.add(areteSelect); //On ajoute l'arete au resultat
                sommets.add(areteSelect.getSommet2()); //On ajoute le nouveau sommet trouvé a la liste des sommet
                areteDispo.remove(areteSelect); //on supprime l'arete selectionnée comme resultat
                i += 1;
            }else{

                while (controleCycle(areteSelect.getSommet1(), areteSelect.getSommet2(), aretesResultat)){ //tant que l'on a pas une arete qui ne fais pas de cycle
                    areteDispo.remove(areteSelect); //on supprime l'arete des arete disponible
                    areteSelect = trouverLaPlusPetiteArete(areteDispo); //On recherche la plus petite arete
                }
                aretesResultat.add(areteSelect); //On ajoute l'arete au resultat
                sommets.add(areteSelect.getSommet2()); //on ajoute le sommet a la liste des sommet decouvert
                areteDispo.remove(areteSelect); //On supprime l'arete des aretes disponible
                i += 1;
            }


        }
        return aretesResultat;
    }



    public static void main(String[] args){
        Graph graphe = new Graph();

        Sommet sommet5 = new Sommet(5);
        Sommet sommet6 = new Sommet(6);
        Sommet sommet8 = new Sommet(8);
        Sommet sommet19 = new Sommet(19);
        Sommet sommet15 = new Sommet(15);

        graphe.addSommet(sommet5);
        graphe.addSommet(sommet6);
        graphe.addSommet(sommet8);
        graphe.addSommet(sommet19);
        graphe.addSommet(sommet15);


        graphe.addArete(2,sommet19,sommet8);
        graphe.addArete(6,sommet19,sommet5);
        graphe.addArete(3,sommet5,sommet8);
        graphe.addArete(12,sommet6,sommet8);
        graphe.addArete(14,sommet6,sommet5);
        graphe.addArete(16,sommet15,sommet6);
        graphe.addArete(1,sommet15,sommet19);

        ArrayList<Arete> resultat;

        //resultat = graphe.algoDeKruskal();
        resultat = graphe.algoDePrim();

        for(int i = 0; i < resultat.size(); ++i){
            System.out.println(resultat.get(i).toString());
        }

    }
}
