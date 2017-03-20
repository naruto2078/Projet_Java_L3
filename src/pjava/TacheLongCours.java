package pjava;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Tache long cours
 *
 * @author Raoul
 */
public class TacheLongCours extends Tache {

    /**
     *
     */
    private Date dateDebut;

    /**
     * Pourcentage d'achevement de la tâche long cours
     */
    private int achevement;

    private long dureeImpartie;


    public TacheLongCours(String titre, int jour, int mois, int annee, int nivImportance, String categorie) {
        super(titre, jour, mois, annee, nivImportance, categorie);
        if (DateUtil.dateCourante().before(this.getEcheance())) {

            this.dateDebut = DateUtil.dateCourante();
            this.achevement = 0;
            this.dureeImpartie = DateUtil.getDateDiff(dateDebut, getEcheance(), TimeUnit.DAYS);
        } else {
            System.out.println("La date de début est impérativement antérieure à la date d'échéance");
            error = true;
        }
    }


    public TacheLongCours(String titre, Date echeance, Date dateDebut, int nivImportance, String categorie) {
        super(titre, echeance, nivImportance, categorie);
        if (dateDebut.before(this.getEcheance())) {
            this.dateDebut = dateDebut;
            this.achevement = 0;
            this.dureeImpartie = DateUtil.getDateDiff(dateDebut, getEcheance(), TimeUnit.DAYS);
        } else {
            System.out.println("La date de debut est imperativement antérieure à la date d'échéance");
            error = true;
        }
    }

    public TacheLongCours(String titre, int jour, int mois, int annee, int jourDebut, int moisDebut, int anneeDebut, int nivImportance, String categorie) {
        super(titre, jour, mois, annee, nivImportance, categorie);
        if (DateUtil.createDate(jour, mois, annee).compareTo(DateUtil.createDate(jourDebut, moisDebut, anneeDebut)) > 0) {

            this.dateDebut = DateUtil.createDate(jourDebut, moisDebut, anneeDebut);
            this.achevement = 0;
            this.dureeImpartie = DateUtil.getDateDiff(dateDebut, getEcheance(), TimeUnit.DAYS);
        } else {
            System.out.println("La date de debut est imperativement antérieure à la date d'échéance");
            error = true;
        }
    }


    public TacheLongCours(String titre, int jour, int mois, int annee, int nivImportance) {
        super(titre, jour, mois, annee, nivImportance);
        if (DateUtil.dateCourante().compareTo(this.getEcheance()) < 0) {

            this.dateDebut = DateUtil.dateCourante();
            this.achevement = 0;
            this.dureeImpartie = DateUtil.getDateDiff(dateDebut, getEcheance(), TimeUnit.DAYS);
        } else {
            System.out.println("La date de début est impérativement antérieure à la date d'échéance");
            error = true;
        }
    }


    public TacheLongCours(String titre, Date echeance, Date dateDebut, int nivImportance) {
        super(titre, echeance, nivImportance);
        if (dateDebut.compareTo(this.getEcheance()) < 0) {
            this.dateDebut = dateDebut;
            this.achevement = 0;
            this.dureeImpartie = DateUtil.getDateDiff(dateDebut, getEcheance(), TimeUnit.DAYS);
        } else {
            System.out.println("La date de debut est imperativement antérieure à la date d'échéance");
            error = true;
        }
    }

    public TacheLongCours(String titre, int jour, int mois, int annee, int jourDebut, int moisDebut, int anneeDebut, int nivImportance) {
        super(titre, jour, mois, annee, nivImportance);
        if (DateUtil.createDate(jour, mois, annee).compareTo(DateUtil.createDate(jourDebut, moisDebut, anneeDebut)) > 0) {

            this.dateDebut = DateUtil.createDate(jourDebut, moisDebut, anneeDebut);
            this.achevement = 0;
            this.dureeImpartie = DateUtil.getDateDiff(dateDebut, getEcheance(), TimeUnit.DAYS);
        } else {
            System.out.println("La date de debut est imperativement antérieure à la date d'échéance");
            error = true;
        }
    }

    /**
     * Si on nomme d la durée impartie pour une tâche
     * (différence entre son échéance et sa date de début),
     * on vérifie l'avancement à chaque pas de d/4 : l'avancement
     * doit être au moins de 25% à d/4, de 50% à d/2, de 75% à 3d/4 et de 100% à d.
     */

    public void verifEstEnRetard() {
        long d = DateUtil.getDateDiff(this.getDateDebut(), DateUtil.dateCourante(), TimeUnit.DAYS);
        if (
                ((d == this.dureeImpartie / 4) && (achevement < 25)) ||
                        ((d == this.dureeImpartie / 2) && (achevement < 50)) ||
                        ((d == 3 * this.dureeImpartie / 4) && (achevement < 75)) ||
                        ((d == this.dureeImpartie) && (achevement < 100)) || DateUtil.dateCourante().after(getEcheance())
                ) {
            setEstEnRetard(true);

        }

    }

    public boolean estEnRetard() {
        verifEstEnRetard();
        return this.getEstEnRetard();
    }

    public int getAchevement() {
        return achevement;
    }

    public void setAchevement(int achevement) {
        if (achevement >= 0 && achevement < 100) {
            this.achevement = achevement;
        } else if (achevement >= 100) {
            this.achevement = 100;
            this.setTacheRealisee();
        }
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public long getDureeImpartie() {
        return dureeImpartie;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDureeImpartie(long dureeImpartie) {
        this.dureeImpartie = dureeImpartie;
    }

    @Override
    public Date getDateInt() {
        if (this.achevement < 25) {
            return DateUtil.ajoutJours(dateDebut, (int) dureeImpartie / 4);
        } else if (this.achevement >= 25 && this.achevement < 50) {
            return DateUtil.ajoutJours(dateDebut, (int) dureeImpartie / 2);
        } else if (this.achevement >= 50 && this.achevement < 75) {
            return DateUtil.ajoutJours(dateDebut, 3 * (int) dureeImpartie / 4);
        } else {
            return getEcheance();
        }

    }


    public String toString() {
        return super.toString() +
                "dateDebut=" + this.dateDebut + "\n" +
                "achevement=" + this.achevement + "\n" +
                "dureImpartie=" + this.dureeImpartie + "\n" +
                "achevement=" + this.achevement + "%\n\n";
    }


}
