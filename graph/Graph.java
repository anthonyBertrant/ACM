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
        System.out.println("On est sur : " +  sommet1.toString() + " on cherche : " + sommetRecherche.toString());
            if(sommet1 == sommetRecherche) {
                System.out.println("IL Y A UN CYCLE !\n");
                return true;
            }
            ArrayList<Arete> listeAreteNonVerif = new ArrayList<>();
            listeAreteNonVerif.addAll(listeArete);
            for(int index = 0 ; index < listeArete.size() ; index++){
                System.out.println("\ti : " + index + " i max : " + listeArete.size() +  " On verifie sur l'arete : " + listeArete.get(index).getSommet1().toString() + " ; " + listeArete.get(index).getSommet2().toString());
                if(listeArete.get(index).getSommet1() == sommet1){
                    listeAreteNonVerif.remove(listeArete.get(index));
                    System.out.println("\t\tSommet1 : On va sur : " +  listeArete.get(index).getSommet2().toString());
                    return controleCycle(listeArete.get(index).getSommet2(), sommetRecherche, listeAreteNonVerif);
                }
                else if(listeArete.get(index).getSommet2() == sommet1){
                    listeAreteNonVerif.remove(listeArete.get(index));
                    System.out.println("\t\tSommet2 : On va sur : " +  listeArete.get(index).getSommet1().toString());
                    return controleCycle(listeArete.get(index).getSommet1(), sommetRecherche, listeAreteNonVerif);
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

            if(!controleCycle(areteTest.getSommet2(),areteTest.getSommet1(),aretesResultat)){
                aretesResultat.add(areteTest); //On ajoute la plus petite arete a notre lot
                i += 1;
            }
            aretes.remove(areteTest); //On la supprime des aretes disponibles
        }
        return aretesResultat;
    }

    public ArrayList<Arete> algoDePrim(){

        //TODO prim
        ArrayList<Arete> aretes = this.listeAretes;
        ArrayList<Arete> aretesResultat = new ArrayList<>();



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

        resultat = graphe.algoDeKruskal();

        for(int i = 0; i < resultat.size(); ++i){
            System.out.println(resultat.get(i).toString());
        }

    }
}
