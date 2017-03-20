package pjava;

import java.util.Date;


/**
 * Tache Ponctuelle
 */
public class TachePonctuelle extends Tache {
    public TachePonctuelle() {
    }

    public TachePonctuelle(String titre, Date echeance, int nivImportance, String categorie) {
        super(titre, echeance, nivImportance, categorie);
    }

    public TachePonctuelle(String titre, int jour, int mois, int annee, int nivImportance, String categorie) {
        super(titre, jour, mois, annee, nivImportance, categorie);
    }

    public TachePonctuelle(String titre, Date echeance, int nivImportance) {
        super(titre, echeance, nivImportance);
    }

    public TachePonctuelle(String titre, int jour, int mois, int annee, int nivImportance) {
        super(titre, jour, mois, annee, nivImportance);
    }


    /**
     * On vérifie si la tâche est en retard ou non
     */
    public void verifestEnRetard() {
        if (!estRealisee) {
            if (this.getEcheance().compareTo(DateUtil.dateCourante()) < 0) {
                setEstEnRetard(true);
            }

        }

    }

    @Override
    public boolean estEnRetard() {
        verifestEnRetard();
        return this.getEstEnRetard();
    }

    @Override
    public Date getDateInt() {
        return getEcheance();
    }

    public String toString() {
        return super.toString() + "\n";
    }
}
