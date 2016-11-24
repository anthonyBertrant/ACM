package graph;

import java.util.ArrayList;

/**
 * Created by anthonybertrant on 10/11/2016.
 * Description: Classe aretes. Genere un type arete pour relier des sommet d'un eventuel graph
 */

public class Arete {

    private int poids;
    private Sommet sommet1;
    private Sommet sommet2;

    /**
     * Construit une arete entre sommet1 et sommet2
     *
     * @param poids
     * @param sommet1
     * @param sommet2
     */
    public Arete(int poids, Sommet sommet1, Sommet sommet2) {
        this.poids = poids;
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;

        sommet1.setArete(this);
        sommet2.setArete(this);
    }

    /*public Arete findLittlestArete(ArrayList<Arete> aretes) {
        Arete areteResult = aretes.get(0);
        for (int i = 1; i < aretes.size() - 1; ++i) {
            if (areteResult.getPoids() > aretes.get(i).getPoids()) {
                areteResult = aretes.get(i);
            }
        }
        return areteResult;
    }*/

    public int getPoids() {
        return poids;
    }

    public ArrayList<Sommet> getAllSommet() {
        return new ArrayList<Sommet>() {{
            add(sommet1);
            add(sommet2);
        }};
    }

    public Sommet getSommet1() {
        return sommet1;
    }

    public Sommet getSommet2() {
        return sommet2;
    }

    public String toStringDev() {
        return "graph.Arete de poids " + poids + ", reliant les sommets " + sommet1 + " et " + sommet2;
    }

    @Override
    public String toString() {
        return sommet1.toString() +" "+ sommet2.toString() ;
    }

    public String toStringPoid(){
        return String.valueOf(poids);
    }
}
