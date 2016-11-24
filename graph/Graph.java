package graph;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by anthonybertrant on 10/11/2016.
 * Description: Classe Graph. Contient notre graph, composé de sommet et d'aretes
 */

public class Graph{

    private ArrayList<Sommet> listeSommets = new ArrayList<>();
    private ArrayList<Arete> listeAretes = new ArrayList<>();

    private void addSommet(Sommet sommet){
        listeSommets.add(sommet);
    }

    private void addArete(int poids, Sommet sommet1, Sommet sommet2){
        listeAretes.add(new Arete(poids, sommet1, sommet2));
    }

    private Arete trouverLaPlusPetiteArete(ArrayList<Arete> aretes){
        Arete areteResult = aretes.get(0);
        for(int i = 1; i < aretes.size(); ++i){
            if(areteResult.getPoids() > aretes.get(i).getPoids()){
                areteResult = aretes.get(i);
            }
        }
        return areteResult;
    }

    private boolean controleCycle(Sommet sommet1, Sommet sommetRecherche, ArrayList<Arete> listeArete){
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

    private ArrayList<Arete> pushArete(Sommet sommet, ArrayList<Arete> aretes){
        //ajoute a un tableau passé en param, la liste de toute les aretes d'un sommet

        for(int i = 0; i < sommet.getListAretes().size(); i++){
            aretes.add(sommet.getListAretes().get(i));
        }

        return aretes;
    }

    private void genererGraph(ArrayList<Arete> aretes){

        org.graphstream.graph.Graph graphique = new SingleGraph("Tutorial 1");
        for(int i = 0; i < listeSommets.size(); i++){
            graphique.addNode(listeSommets.get(i).toString());
        }

        for(int i = 0; i < aretes.size(); i++) {
            //graphique.addEdge(aretes.get(i).toStringPoid(), aretes.get(i).getSommet1().toString(), aretes.get(i).getSommet2().toString());
            graphique.addEdge(String.valueOf(i), aretes.get(i).getSommet1().toString(), aretes.get(i).getSommet2().toString())
                     .addAttribute(String.valueOf(i),aretes.get(i).toStringPoid());

        }


        for (Node node : graphique) { //Afficher le nom des noeud
            node.addAttribute("ui.label", node.getId());
        }
        int i = 0;
        for(Edge e:graphique.getEachEdge()) { //Afficher le poid des aretes
            //e.addAttribute("ui.label", e.getId());
            e.addAttribute("ui.label", e.getAttribute(String.valueOf(i)));
            ++i;
        }

        graphique.display();
    }

    private boolean trouverSommet(Sommet sommet,ArrayList<Sommet> sommetsList){
        //Cherche si un sommet est present dans une list de sommets
        for(int i = 0; i < sommetsList.size(); ++i){
            if(sommetsList.get(i) == sommet){
                return true;
            }
        }

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

    public ArrayList<Arete> algoDePrim(){

        //TODO prim regarder au niveau de verifCycle (si le sommet que l'on veux ajouter est deja present dans nos
        //TODO sommet deja ajoutés, on a un cycle)

        ArrayList<Arete> aretesResultat = new ArrayList<>(); //contient les aretes du graph minimal
        ArrayList<Arete> areteDispo = new ArrayList<>(); //Aretes disponible a la selection a un instant t
        ArrayList<Sommet> sommets = new ArrayList<>(); //contient les sommets du graph que l'on a explorer

        sommets.add(listeSommets.get(4)); //selection du premier sommet

        int i = 0;

        pushArete(sommets.get(i), areteDispo); //ajoute la liste des  aretes d'un sommet dans notre tableau   On est sur le sommet i

        while (aretesResultat.size() != listeSommets.size() - 1){

            areteDispo.removeAll(aretesResultat); //on supprime les chemins deja selectionnées comme resultat

            Arete areteSelect = trouverLaPlusPetiteArete(areteDispo);

            if(!(trouverSommet(areteSelect.getSommet1(), sommets) && trouverSommet(areteSelect.getSommet2(), sommets))){
                aretesResultat.add(areteSelect);
                if(trouverSommet(areteSelect.getSommet1(), sommets)){
                    sommets.add(areteSelect.getSommet2());
                }else{
                    sommets.add(areteSelect.getSommet1());
                }
                areteDispo.remove(areteSelect);

                if(i != listeSommets.size()){  //Si on a pas encore ajouter tout les sommets
                    i += 1;
                    pushArete(sommets.get(i), areteDispo); //ajoute la liste des aretes d'un sommet dans notre tableau   On est sur le sommet i
                }

            }else{
                areteDispo.remove(areteSelect);
            }
        }
        return aretesResultat;
    }

    /*---------- FIN FONCTIONS DE TRI -------*/


    public static void main(String[] args){
        Graph graphe = new Graph();

        Sommet sommet5  = new Sommet(5);
        Sommet sommet6  = new Sommet(6);
        Sommet sommet8  = new Sommet(8);
        Sommet sommet19 = new Sommet(19);
        Sommet sommet15 = new Sommet(15);
        Sommet sommet12 = new Sommet(12);
        Sommet sommet32 = new Sommet(32);
        Sommet sommet42 = new Sommet(42);

        graphe.addSommet(sommet5);
        graphe.addSommet(sommet6);
        graphe.addSommet(sommet8);
        graphe.addSommet(sommet19);
        graphe.addSommet(sommet15);
        graphe.addSommet(sommet12);
        graphe.addSommet(sommet32);
        graphe.addSommet(sommet42);

        graphe.addArete(2 ,sommet19 ,sommet8);
        graphe.addArete(6 ,sommet19 ,sommet5);
        graphe.addArete(3 ,sommet5  ,sommet8);
        graphe.addArete(12,sommet6  ,sommet8);
        graphe.addArete(14,sommet6  ,sommet5);
        graphe.addArete(16,sommet15 ,sommet6);
        graphe.addArete(1 ,sommet15 ,sommet19);
        graphe.addArete(4 ,sommet32 ,sommet42);
        graphe.addArete(19,sommet42 ,sommet19);
        graphe.addArete(7 ,sommet12 ,sommet42);
        graphe.addArete(13,sommet12 ,sommet8);
        graphe.addArete(18,sommet32 ,sommet15);
        graphe.addArete(18,sommet6 ,sommet42);


        ArrayList<Arete> resultat = new ArrayList<>();

        int choix = 0;

        System.out.println("Entrer votre choix: 0 pour Kruskal, 1 pour Prim, 2 pour afficher le graph brute: ");
        Scanner sc = new Scanner(System.in);
        choix = sc.nextInt();

        switch(choix){
            case 0:
                resultat = graphe.algoDeKruskal();
                break;

            case 1:
                resultat = graphe.algoDePrim();
                break;

            case 2:
                resultat = graphe.listeAretes;
                break;

            default:
                break;
        }

        for (Arete aResultat : resultat) {
            System.out.println(aResultat.toStringDev());
        }

        graphe.genererGraph(resultat);

    }
}
