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

    public boolean controleCycle(Arete areteTest, ArrayList<Arete> aretesResultat){
        int j =0;
        Sommet sommet1 = areteTest.getSommet1();
        Sommet sommet2 = areteTest.getSommet2();

        for(int i = 0; i < aretesResultat.size(); i++){
            if(sommet1 == aretesResultat.get(i).getSommet1() || sommet1 == aretesResultat.get(i).getSommet2() ||
                    sommet2 == aretesResultat.get(i).getSommet1() || sommet2 == aretesResultat.get(i).getSommet2()){
                return false;
            }
        }
        return true;
    }

    /*---------- FONCTIONS DE TRI ----------*/

    public ArrayList<Arete> algoDeKruskal(){
        ArrayList<Arete> aretes = this.listeAretes;
        ArrayList<Arete> aretesResultat = new ArrayList<>();

        Arete areteTest;

        /* On explore l'ensemble des aretes, jusqu'a ce qu'on atteigne le nb de sommet*/
        int i;
        for(i = 0; i < listeSommets.size()-1; ++i){
            areteTest = trouverLaPlusPetiteArete(aretes);
            if(controleCycle(areteTest,aretesResultat)){
                aretesResultat.add(areteTest); //On ajoute le plus petite arete a notre lot
                aretes.remove(areteTest); //On la supprime des aretes disponibles
            }
            aretes.remove(areteTest);
        }
        return aretesResultat;
    }

    public ArrayList<Arete> algoDePrim(){
        ArrayList<Arete> aretes = this.listeAretes;
        ArrayList<Arete> aretesResultat = new ArrayList<>();

        return aretesResultat;
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

        graphe.afficherAretes();
        graphe.afficherSommets();

        ArrayList<Arete> resultat;

        //System.out.println(graphe.listeSommets.size());

        //System.out.println(graphe.findLittlestArete(graphe.listeAretes).toString());
        resultat = graphe.algoDeKruskal();

        for(int i = 0; i < resultat.size(); ++i){
            System.out.println(resultat.get(i).toString());
        }

    }
}
