package pjava;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant les différentes catégories de tâches
 * Les catégories sont des chaînes de caractères
 */
public class Categories implements Serializable {


    private ArrayList<String> lcategorie = new ArrayList<>();

    public Categories() {
        lcategorie.add("Travail");
        lcategorie.add("Personnel");
    }

    public ArrayList<String> getLcategorie() {
        return lcategorie;
    }

    /**
     * @param cat: Catégorie à ajouter
     * @return message disant si l'ajout s'est bien passé ou non
     */
    public String ajouterCat(String cat) {
        if (cat.length() == 0) {
            return "Une catégorie doit posséder un nom";
        }
        if (cat.length() == 1) {
            return "Une catégorie doit posséder minimum 2 lettres";
        } else {
            String catCorrect = cat.substring(0, 1).toUpperCase() + cat.substring(1).toLowerCase();
            if (lcategorie.contains(catCorrect))
                return "Votre catégorie existe déjà";
            else
                lcategorie.add(catCorrect);

            return "Votre catégorie à été ajouté correctement";
        }
    }

    public void afficheLcat() {
        System.out.println("Catégories:");

        for (int i = 0; i < lcategorie.size(); i++) {
            System.out.println(lcategorie.get(i));
        }

    }

    public boolean existCat(String cat) {
        if (cat.length() == 0) {
            return false;
        }
        if (cat.length() == 1) {
            return false;
        } else {
            String catCorrect = cat.substring(0, 1).toUpperCase() + cat.substring(1).toLowerCase();
            if (lcategorie.contains(catCorrect))
                return true;
            return false;
        }

    }

    /**
     * @param cat: catégorie à supprimer
     * @return : message disant si l'ajout s'est bien passé ou non
     */
    public String supprimerCat(String cat) {
        if (cat.length() > 1 && lcategorie.remove(cat)) {
            return "Catégorie supprimée correctement";
        } else {
            return "Catégorie non supprimée, êtes vous sur de l'existence de celle ci?";
        }
    }

    /**
     * @param ancienne catégorie à modifier
     * @param nouvelle sa nouvelle valeur
     */
    public void modifierCat(String ancienne, String nouvelle) {
        int index = lcategorie.indexOf(ancienne);
        lcategorie.set(index, nouvelle);
    }

}
