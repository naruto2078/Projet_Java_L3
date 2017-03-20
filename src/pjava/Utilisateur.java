package pjava;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe représentant un utilisateur
 *
 */

@SuppressWarnings({"unused", "serial"})
public class Utilisateur implements Serializable {

    /**
     * Fichier où sont stockées les données
     */
    private String nomFichier = "user.txt";



    /**
     * Liste des tâches créées par l'utilisateur
     */
    private ListeTaches ltaches = new ListeTaches();


    private Categories categories = new Categories();

    public Utilisateur(){
        Utilisateur u = getUtilisateur();
        if(u==null){
            ltaches = new ListeTaches();
            categories = new Categories();
        }
        else{
            this.ltaches = u.ltaches;
            this.categories = u.categories;
        }

    }

    /**
     * Met à jour le fichier où sont stockées les informations
     */
    public void update() {

        ObjectOutputStream ecriture = null;
        File f = new File(nomFichier.toString());
        try {
            ecriture = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
            ecriture.writeObject(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Fichier non trouvé");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ecriture != null)
                    ecriture.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Lis le fichier user.txt et crée une instance d'utilisateur
     */
    public Utilisateur getUtilisateur() {
        ObjectInputStream lecture = null;
        Utilisateur utilisateur = null;
        File f = new File(nomFichier.toString());
        try {
            if(f.exists() && f.length()>0){
                lecture = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
                utilisateur = (Utilisateur) lecture.readObject();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (lecture != null)
                    lecture.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return utilisateur;
    }

    public Categories getCategories() {
        return categories;
    }



    public ListeTaches getLtaches() {
        return ltaches;
    }

    public int getnbTaches() {
        return ltaches.getnbTaches();
    }

    public String ajouteTache(Tache t) {
        String ajout= ltaches.ajouteTache(t);
        update();
        return  ajout;
    }

    public boolean existeDeja(String titret) {
        return ltaches.existeTache(titret);

    }


    public String supprimerTacheNom(String nomTache) {
        String str= ltaches.supprimerTacheNom(nomTache);
        update();
        return str;
    }

    public String supprimerTacheObjet(Tache t) {
        String str= ltaches.supprimerTacheObjet(t);
        update();
        return str;
    }

    public String supprimerTacheInd(int i) {
        String str = ltaches.supprimerTacheInd(i);
        update();
        return str;
    }

    public Tache getTacheNom(String nomTache) {
        return ltaches.getTacheNom(nomTache);
    }

    public Tache getTacheObjet(Tache t) {
        return ltaches.getTacheObjet(t);
    }

    public Tache getTacheInd(int i) {
        return ltaches.getTacheInd(i);
    }


       public String toString() {
        return ltaches.toString();
    }

    public void triTachesEcheance() {
        ltaches.triTachesEcheance();
    }

    public void triEcheanceInt() {
        ltaches.triEcheanceInt();
    }



    public String ajouterCat(String cat) {
        String ajout= categories.ajouterCat(cat);
        update();
        return ajout;
    }

    public String supprimerCat(String cat) {
        for (Tache t : ltaches.getlTaches()) {
            if (t.getCategorie() == cat) {
                t.setCategorie(" ");
            }
        }
        String str= categories.supprimerCat(cat);
        update();
        return str;
    }

    public void modifierCat(String ancienne, String nouvelle) {
        categories.modifierCat(ancienne, nouvelle);
        update();
    }

}
