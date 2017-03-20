package pjava;

import java.util.Collections;
import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class ListeTaches implements Serializable {

    /*
    * Contient information trivial pour le bilan
   */
    private int totalTachesARPeriode; //total taches à realisé dans la periode
    private int totalTachesDRPeriode; //total taches deja realisé dans la periode
    private int totalTachesDRRPeriode;//total taches deja realisé en retart dans la periode


    private ArrayList<Tache> lTachesDRealiserPeriod = new ArrayList<Tache>();
    private ArrayList<Tache> lTaches = new ArrayList<Tache>();
    private ArrayList<Tache> lTachesARealiserPeriod = new ArrayList<Tache>();
    private ArrayList<Tache> lTachesTotalDRealiser=new ArrayList<>();

    public ListeTaches() {
    }

    @SuppressWarnings("unused")


    public ListeTaches(ArrayList<Tache> lTaches) {
        this.lTaches = lTaches;
    }


    public Tache getTacheNom(String titret) {
        for (Tache t : lTaches) {
            if (titret.equals(t.getTitre())) {
                return t;
            }
        }
        return null;
    }

    public int getnbTaches() {
        return lTaches.size();
    }

    public Tache getTacheObjet(Tache ta) {
        for (Tache t : lTaches) {
            if (t == ta) {
                return t;
            }
        }
        return null;
    }

    public Tache getTacheInd(int i) {
        if (i < lTaches.size())
            return lTaches.get(i);
        return null;

    }

    /**
     * Liste triée par date d'échéance
     */
    public void triTachesEcheance() {
        Collections.sort(lTaches, new ComparatorEcheance());
    }

    /**
     * Liste triée par date d'échéance intermédiaire
     */
    public void triEcheanceInt() {

        Collections.sort(lTaches, new ComparateurDateInt());

    }


    public void echanger888(int ind) {
        Tache t = lTaches.get(ind);
        lTaches.remove(ind);
        lTaches.add(0, t);
    }

    /**
     * tri où on réunit 8 taches, 1 importante, 3 moyennes et 5 petite.
     */
    public void tri888()
    {

        triTachesEcheance();

        int cptTImp = 0;// Compteur de taches importantes.
        int cptTInt = 0;// Compteur de taches moyenne.
        int cptTBas = 0;// Compteur de taches basse.
        int i = 0;


        while (i < lTaches.size() && (cptTBas < 5)) //si on a parcourue tout le tableau ou
        {                                                            //alors on a reunni le nombre de taches requis on s'arrete
            if ((cptTBas < 5) && (lTaches.get(i).getNivImportance() == 3)) {
                System.out.println("je trouve " + lTaches.get(i).getTitre() + "avec niv=" +
                        lTaches.get(i).getNivImportance());
                echanger888(i);
                cptTBas++;

            }
            i++;
        }
        i = 0;
        while (i < lTaches.size() && (cptTInt < 3)) //si on a parcourue tout le tableau ou
        {

            if ((cptTInt < 3) && (lTaches.get(i).getNivImportance() == 2)) {
                System.out.println("je trouve " + lTaches.get(i).getTitre() + "avec niv=" +
                        lTaches.get(i).getNivImportance());//alors on a reunni le nombre de taches requis on s'arrete
                echanger888(i);
                cptTInt++;
            }
            i++;
        }
        i = 0;

        while (i < lTaches.size() && (cptTImp < 1)) //si on a parcourue tout le tableau ou
        {

            if ((cptTImp < 1) && (lTaches.get(i).getNivImportance() == 1)) {

                //tImpT8 = lTaches.get(i);
                echanger888(i);
                cptTImp++;
            }

            i++;
        }


    }




    public String ajouteTache(Tache t) {

        lTaches.add(t);
        return "Tache correctement ajouté";
    }

    public boolean existeTache(String titret) {
        for (Tache t : lTaches) {
            if (titret.equals(t.getTitre())) {
                return true;
            }
        }
        return false;

    }

    public String supprimerTacheNom(String nomTache) {
        for (int i = 0; i < lTaches.size(); i++) {
            if (nomTache.equals(lTaches.get(i).getTitre())) {
                if (lTaches.remove(i) != null)
                    return "Tache correctement supprimé.";

            }

        }
        return "Objet non effacé, est vous sure de l'existence de la tache?";
    }

    public String supprimerTacheObjet(Tache t) {
        for (int i = 0; i < lTaches.size(); i++) {
            if (t == lTaches.get(i)) {
                if (lTaches.remove(i) != null)
                    return "Tache correctement supprimé.";

            }

        }
        return "Objet non effacé, est vous sure de l'existence de la tache?";
    }

    public String supprimerTacheInd(int i) {

        if (lTaches.remove(i) != null)
            return "Tache correctement supprimé.";

        return "Objet non effacé, est vous sure de l'existence de la tache?";
    }

    public String toString() {
        return "liste de taches contiens:\n" + lTaches.toString();
    }


    public ArrayList<Tache> getlTaches() {
        return lTaches;
    }

    public void tachesdRealiseTotal()
    {

        for (Tache t : lTaches) {
            if (t.estRealisee && !lTachesTotalDRealiser.contains(t)) {
                lTachesTotalDRealiser.add(t);
            }

        }

    }

    public int verifTachesdRealise(Date datedeb, Date datefin) {
        lTachesDRealiserPeriod.clear();
        int totalTachesDRPeriode = 0; //total taches deja realisé dans la periode
        for (Tache t : lTachesTotalDRealiser) {
            if (t.getDateInt().before(datefin) && t.getDateInt().after(datedeb) && t.estRealisee) {
                lTachesDRealiserPeriod.add(t);

                totalTachesDRPeriode++;
            }

        }
        return totalTachesDRPeriode;
    }

    public int verifTachesaRealise(Date datedeb, Date datefin) {
        lTachesARealiserPeriod.clear();
        int totalTachesARPeriode = 0; //total taches deja realisé dans la periode
        for (Tache t : lTaches) {

            if (t.getDateInt().before(datefin) && t.getDateInt().after(datedeb) && !t.estRealisee) {
                lTachesARealiserPeriod.add(t);
                totalTachesARPeriode++;

            }

        }
        return totalTachesARPeriode;
    }

    public int verifTachesdRealiseR(Date datedeb, Date datefin) {
        int totalTachesDRealiseR = 0; //nombre de taches deja realisé mais en retard;

        for (Tache t : lTachesDRealiserPeriod) {
            if (t.estEnRetard()) {
                totalTachesDRealiseR++;
            }

        }
        return totalTachesDRealiseR;
    }
    public void triEcheanceInterTacheRealiseTotal()
    {
        Collections.sort(lTachesTotalDRealiser, new ComparateurDateInt());
    }

    public void bilan(Date datedeb, Date datefin) {

        tachesdRealiseTotal();
        totalTachesARPeriode = verifTachesaRealise(datedeb, datefin);

        totalTachesDRPeriode = verifTachesdRealise(datedeb, datefin);

        totalTachesDRRPeriode = verifTachesdRealiseR(datedeb, datefin);

    }

    public ArrayList<Tache> getlTachesARealiserPeriod() {
        return lTachesARealiserPeriod;
    }

    public int getTsDRealPer() {
        return totalTachesDRPeriode;
    }

    public int getTsARealPer() {
        return totalTachesARPeriode;
    }

    public int getTsDRRealPer() {
        return totalTachesDRRPeriode;
    }
}





