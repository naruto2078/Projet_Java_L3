package pjava;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Représente une tâche
 *
 * @author Raoul, Michael
 */
@SuppressWarnings("unused")


public abstract class Tache implements Serializable {

    private Boolean estEnRetard = false;


    /**
     * Titre de la tâche
     */
    private String titre;


    /**
     * Catégorie de la tâche
     */
    private String categorie = "Personnel";

    /**
     * Date d'échéance de la tâche
     */
    private Date echeance;


    /**
     * Niveau d'importance de la tâche courante
     */
    private int nivImportance;


    /**
     * erreur de l'instanciation d'une tâche
     */
    public boolean error = false;

    /**
     * Indique si une tâche est réalisée ou non
     */

    public boolean estRealisee = false;


    public Tache() {
    }


    public Tache(String titre, Date echeance, int nivImportance, String categorie) {
        this.titre = titre;
        this.echeance = echeance;
        this.nivImportance = nivImportance;
        this.categorie = categorie;
    }

    public Tache(String titre, int jour, int mois, int annee, int nivImportance, String categorie) {
        this.titre = titre;
        this.echeance = DateUtil.createDate(jour, mois, annee);
        this.nivImportance = nivImportance;
        this.categorie = categorie;
    }

    public Tache(String titre, int jour, int mois, int annee, String categorie) {
        this.titre = titre;
        this.echeance = DateUtil.createDate(jour, mois, annee);
        this.categorie = categorie;
    }

    public Tache(String titre, Date echeance, int nivImportance) {
        this.titre = titre;
        this.echeance = echeance;
        this.nivImportance = nivImportance;
    }

    public Tache(String titre, int jour, int mois, int annee, int nivImportance) {
        this.titre = titre;
        this.echeance = DateUtil.createDate(jour, mois, annee);
        this.nivImportance = nivImportance;
    }

    public Tache(String titre, int jour, int mois, int annee) {
        this.titre = titre;
        this.echeance = DateUtil.createDate(jour, mois, annee);
    }

    public String getTitre() {
        return titre;
    }

    public Date getEcheance() {
        return echeance;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setEstEnRetard(boolean r) {
        this.estEnRetard = r;
    }

    public boolean getEstEnRetard() {
        return this.estEnRetard;
    }

    /**
     * Change la date d'échéance
     *
     * @param echeance nouvelle date d'échéance
     */
    public void setEcheance(Date echeance) {
        if (echeance.after(this.echeance)) {
            this.echeance = echeance;
            this.estEnRetard = false;
        }
    }


    public void setTacheRealisee() {
        estRealisee = true;
    }

    public boolean getTacheRealisee() {
        return estRealisee;
    }

    /**
     * Renvois le niveau d'importance de la tâche courante.
     */
    public int getNivImportance() {
        return nivImportance;
    }

    /**
     * Modifie le niveau d'importance de la tâche courante.
     */
    public String setNivImportance(int niv) {

        if (niv > 0 && niv <= 3) {
            nivImportance = niv;
            return "Niveau modifié correctement";
        }

        return "Erreur, niveau non modifié";

    }


    /**
     * @return retourne vrai si la tâche est en retard, faux sinon
     */
    public abstract boolean estEnRetard();

    /**
     * Obtenir la date intermédiaire
     *
     * @return retourne la date d'échéance intermédiaire
     */
    public abstract Date getDateInt();

    public String toString() {
        return "titreTache=" + this.titre + "\n" +
                "dateDebut=" + DateUtil.dateCourante().toString() + "\n" +
                "dateEcheance=" + this.echeance.toString() + "\n" +
                "nivImportance=" + this.nivImportance + "\n" +
                "dateIntermediare=" + this.getDateInt() + "\n";
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }
}
