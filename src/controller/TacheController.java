package controller;


import pjava.*;
import views.TacheView;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

/**
 * Contrôleur de tâche: fait la liaison entre la vue(TacheView) et le Modèle(ListeTache)
 * Le modèle est initialisé à partir de la liste de tâches de l'utilisateur
 * Les différents éléments de la vue sont quant à eux générés à partir de la liste de tâches et de la liste de Catégorie de l'utilisateur
 */
@SuppressWarnings("unused")
public class TacheController {
    private TacheView view;
    private ListeTaches listeTachesModel;
    private Utilisateur utilisateur;
    private int ind;
    private int lenbTacheaAff;
    private int derindModindAff;
    private boolean evenDel=false;

    public TacheController(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.listeTachesModel = utilisateur.getLtaches();
        ind = 0;
        lenbTacheaAff=12;
        derindModindAff=0;
        initView();

        //évènements gérant les tris de tâches
        view.getTriDateEchItem().addActionListener(new ActionListener() {
            /**
             * Génération du tri par date d'échéance lorsqu'on clique sur le bouton correspondant
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                utilisateur.triTachesEcheance();
                //listeTachesModel.triTachesEcheance();
                listeTachesModel = utilisateur.getLtaches();
                //view.setButtons(getButtons(listeTachesModel.getlTaches()));
                view.setTitle("Tri par date d'échéance");
                //afficheViewContro(12);
                setLenbTacheaAff(12);
                pagReset();
            }
        });
        view.getTriDateIntItem().addActionListener(new ActionListener() {
            /**
             * Génération du tri par date d'échéance intermédiaire lorsqu'on clique sur le bouton correspondant
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                listeTachesModel.triEcheanceInt();
                //view.setButtons(getButtons(listeTachesModel.getlTaches()));
                view.setTitle("Tri par date intermédiaire");
                setLenbTacheaAff(12);
                pagReset();
            }
        });
        view.getTri8Item().addActionListener(new ActionListener() {
            @Override
            /**
             * Génération du tri alternatif lorsqu'on clique sur le bouton correspondant
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                listeTachesModel.tri888();

                setLenbTacheaAff(8);
                pagReset();
            }
        });


        pagSuiv();

        //évènements qui gèrent l'ajout d'une tâche
        view.getAjouterItem().addActionListener(new AjoutTacheListener());
        view.getAjoutCatItem().addActionListener(new AjoutCategorieListener());
        view.getModifierCatItem().addActionListener(new ModifierCategorieListener());
        view.getSupprimerCatItem().addActionListener(new SupprimerCategorieListener());
        view.getBilanItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("je boucle dans caracteristique");
                ArrayList<JPanel> ljp = new ArrayList<JPanel>();


                MaskFormatter dateF = null;

                try {

                    dateF = new MaskFormatter("##-##-####");
                } catch (ParseException e2) {


                    e2.printStackTrace();
                }

                JFormattedTextField datedeb, datefin;
                JLabel labeldatedeb = new JLabel("Date debut");
                JLabel labeldatefin = new JLabel("Date fin");


                datedeb = new JFormattedTextField(dateF);

                datefin = new JFormattedTextField(dateF);


                JPanel saisieDdebfin = new JPanel();

                JPanel saisieDdeb = new JPanel();

                JPanel saisieDfin = new JPanel();

                saisieDdebfin.setLayout(new GridLayout(0, 1));

                saisieDdeb.setLayout(new GridLayout(0, 1));

                saisieDfin.setLayout(new GridLayout(0, 1));

                saisieDdeb.add(labeldatedeb);

                saisieDdeb.add(datedeb);


                saisieDfin.add(labeldatefin);

                saisieDfin.add(datefin);


                JButton valider = new JButton("Valider");

                valider.setSize(new Dimension(50, 50));

                saisieDdebfin.add(saisieDdeb);

                saisieDdebfin.add(saisieDfin);

                saisieDdebfin.add(valider);


                ljp.add(saisieDdebfin);

                valider.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        listeTachesModel.triEcheanceInterTacheRealiseTotal();
                        boolean bonneDate = false;

                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                        Date datedebsaisie = null, datefinsaisie = null;

                        try {

                            datedebsaisie = formatter.parse(datedeb.getText());

                            datefinsaisie = formatter.parse(datefin.getText());


                            //datedebsaisie=formatter.parse("04-12-2016");

                            //datefinsaisie=formatter.parse("17-12-2016");


                            bonneDate = true;

                        } catch (ParseException e1) {

                            bonneDate = false;

                        }


                        if (bonneDate) {
                            //afficheBilan(datedebsaisie,datefinsaisie);
                            try {

                                ljp.add(afficheBilan(datedebsaisie, datefinsaisie));

                            } catch (ParseException e1) {

                                e1.printStackTrace();

                            }
                            System.out.println("coucou");
                            //System.out.println(ljp);
                            view.refreshjp(ljp);
                            while (ljp.size() >= 2) {
                                ljp.remove(1);
                            }
                        }
                    }
                });
                ljp.add(saisieDdebfin);
                view.refreshjp(ljp);
            }
        });
    }

    /**
     * Ecouteur gérant l'ajout de tâches
     */
    class AjoutTacheListener implements ActionListener {
        JTextField titre = new JTextField();
        JComboBox typeTaches;
        JComboBox categories;
        JFormattedTextField echeance;
        JFormattedTextField debut;
        JFormattedTextField importance;
        JButton valider;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Ajout de tache");
            JLabel labelTitre = new JLabel("Titre");
            JLabel labelCateg = new JLabel("Catégorie");
            JLabel labelEcheance = new JLabel("Echéance");
            JLabel labelDebut = new JLabel("Date de début");
            JLabel labelImportance = new JLabel("Niveau d'importance (1:élevé 2:moyen 3:bas)");
            JLabel labelTypeTache = new JLabel("Type de tâches");
            titre = new JTextField();
            ArrayList<String> lCateg = utilisateur.getCategories().getLcategorie();
            String[] taches = {"TachePonctuelle", "TacheLongCours"};
            typeTaches = new JComboBox(taches);
            categories = new JComboBox();
            for (String categorie : lCateg) {
                categories.addItem(categorie);
            }
            MaskFormatter dateF = null, importanceF = null, debutF = null;
            try {
                dateF = new MaskFormatter("##-##-####");
                debutF = new MaskFormatter("##-##-####");
                dateF.setPlaceholder("#");
                debutF.setPlaceholder("#");
                importanceF = new MaskFormatter("#");
                importanceF.setPlaceholder("#");

            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            echeance = new JFormattedTextField(dateF);
            debut = new JFormattedTextField(debutF);
            importance = new JFormattedTextField(importanceF);

            JPanel panTitre = new JPanel();
            panTitre.setPreferredSize(new Dimension(100, 40));
            JPanel panCateg = new JPanel();
            JPanel panEcheance = new JPanel();
            JPanel panImportance = new JPanel();
            JPanel pan = new JPanel();
            view.removeButtons();
            pan.setLayout(new GridLayout(0, 1));

            pan.add(labelTitre);
            pan.add(titre);
            pan.add(labelTypeTache);
            pan.add(typeTaches);
            pan.add(labelCateg);
            pan.add(categories);
            pan.add(labelEcheance);
            pan.add(echeance);
            pan.add(labelDebut);
            pan.add(debut);
            pan.add(labelImportance);
            pan.add(importance);

            valider = new JButton("Valider");
            valider.addActionListener(new AjoutTacheValidation());


            ArrayList<JPanel> panels = new ArrayList<>();
            valider.setPreferredSize(new Dimension(50, 20));
            pan.add(valider);
            //view.getContainer().updateUI();
            panels.add(pan);
            view.getContainer().setLayout(new GridLayout(0, 1));
            view.refreshjp(panels);
        }

        private class AjoutTacheValidation implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titreT = titre.getText();
                String categT = categories.getSelectedItem().toString();
                String typeT = "pjava." + typeTaches.getSelectedItem().toString();
                int importanceT = Character.getNumericValue(importance.getText().charAt(0));

                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date echeanceT = null, debutT = null;
                try {
                    echeanceT = formatter.parse(echeance.getText());
                    debutT = formatter.parse(debut.getText());
                } catch (ParseException e1) {
                    //e1.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
               try {
                   cal.setTime(echeanceT);
               }
               catch(Exception e1)
               {

               }
                echeanceT = cal.getTime();
                Calendar cal2 = Calendar.getInstance();
                try{
                    cal2.setTime(debutT);
                }catch(Exception e1){};
                debutT = cal2.getTime();

                try {
                    Class c = Class.forName(typeT);
                    Constructor constructor;
                    Object tacheCree;
                    System.out.println(typeT == "pjava.TachePonctuelle" + "========");
                    if (c.getName() == "pjava.TachePonctuelle") {
                        constructor = c.getConstructor(String.class, Date.class, int.class, String.class);
                        tacheCree = constructor.newInstance(titreT, echeanceT, importanceT, categT);
                        if (!((TachePonctuelle) tacheCree).error) {
                            utilisateur.ajouteTache(((TachePonctuelle) tacheCree));
                        } else {
                            String info = "La date de debut est imperativement antérieure à la date d'échéance";
                            JOptionPane jop = new JOptionPane();
                            jop.showMessageDialog(null, info, "Ajout tâche", JOptionPane.INFORMATION_MESSAGE);

                        }
                    } else {
                        constructor = c.getConstructor(String.class, Date.class, Date.class, int.class, String.class);
                        tacheCree = constructor.newInstance(titreT, echeanceT, debutT, importanceT, categT);
                        if (!((TacheLongCours) tacheCree).error) {
                            utilisateur.ajouteTache(((TacheLongCours) tacheCree));
                        } else {
                            String info = "La date de debut est imperativement antérieure à la date d'échéance";
                            JOptionPane jop = new JOptionPane();
                            jop.showMessageDialog(null, info, "Ajout tâche", JOptionPane.INFORMATION_MESSAGE);

                        }
                    }
                    //System.out.println(((TacheLongCours)tacheCree).getDateDebut());

                    pagCour();
                } catch (ClassNotFoundException e1) {
                    //e1.printStackTrace();
                } catch (NoSuchMethodException e1) {
                    //e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    //e1.printStackTrace();
                } catch (InstantiationException e1) {
                    //e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    //e1.printStackTrace();
                }

                System.out.println("Tache " + titreT + " " + categT + " " + typeT + " " + importanceT + " " + echeanceT + "   " + debutT);
            }
        }
    }

    /**
     * Ecouteur gérant la suppression de tâches
     */
    class ModifierTacheListener implements ActionListener {
        Tache tache;

        JTextField titre = new JTextField();
        JComboBox categories;
        JFormattedTextField echeance;
        JFormattedTextField debut;
        JFormattedTextField importance;
        JTextField achevement;
        JCheckBox estRealisee;
        JButton valider;

        public ModifierTacheListener() {
        }

        public ModifierTacheListener(Tache tache) {
            this.tache = tache;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println(tache.estEnRetard() && tache.estRealisee);
            JLabel labelTitre = new JLabel("Titre");
            JLabel labelCateg = new JLabel("Catégorie");
            JLabel labelEcheance = new JLabel("Echéance");
            JLabel labelDebut = new JLabel("Date de début");
            JLabel labelImportance = new JLabel("Niveau d'importance (1:élevé 2:moyen 3:bas)");
            JLabel labelTypeTache = new JLabel("Type de tâches : " + tache.getClass().getSimpleName().toString());
            JLabel labelAchevement = new JLabel("Pourcentage réalisé");
            JLabel labelEstRealisee = new JLabel("Tâche réalisée");
            titre = new JTextField();
            ArrayList<String> lCateg = utilisateur.getCategories().getLcategorie();
            String[] taches = {"TachePonctuelle", "TacheLongCours"};
            categories = new JComboBox();
            for (String categorie : lCateg) {
                categories.addItem(categorie);
            }
            MaskFormatter dateF = null, importanceF = null, debutF = null, achevementF = null;
            try {
                dateF = new MaskFormatter("##-##-####");
                debutF = new MaskFormatter("##-##-####");
                dateF.setPlaceholder("#");
                debutF.setPlaceholder("#");
                importanceF = new MaskFormatter("#");
                importanceF.setPlaceholder("#");
                achevementF = new MaskFormatter("#");
                achevementF.setPlaceholder("#");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            echeance = new JFormattedTextField(dateF);
            debut = new JFormattedTextField(debutF);
            importance = new JFormattedTextField(importanceF);
            achevement = new JTextField();
            estRealisee = new JCheckBox("Tâche réalisée");
            JPanel panTitre = new JPanel();
            panTitre.setPreferredSize(new Dimension(100, 40));
            JPanel panCateg = new JPanel();
            JPanel panEcheance = new JPanel();
            JPanel panImportance = new JPanel();
            JPanel pan = new JPanel();
            view.removeButtons();
            pan.setLayout(new GridLayout(0, 1));

            titre.setText(tache.getTitre());
            categories.setSelectedItem(tache.getCategorie());


            pan.add(labelTitre);
            pan.add(titre);
            pan.add(labelTypeTache);
            pan.add(labelCateg);
            pan.add(categories);
            pan.add(labelEcheance);
            pan.add(echeance);
            if (tache.getClass().getSimpleName().equals("TacheLongCours")) {
                pan.add(labelDebut);
                pan.add(debut);
                pan.add(labelAchevement);
                pan.add(achevement);
            } else {
                if (!tache.estRealisee) {
                    pan.add(labelEstRealisee);
                    pan.add(estRealisee);
                }
            }

            pan.add(labelImportance);
            pan.add(importance);
            importance.setValue(tache.getNivImportance());

            /*********/
            String titreT = tache.getTitre();
            String categT = tache.getCategorie();
            int importanceT = tache.getNivImportance();

            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date echeanceT = null, debutT = null;
            try {
                echeanceT = formatter.parse(formatter.format(tache.getEcheance()));
                if (tache.getClass().getSimpleName().equals("TacheLongCours")) {
                    debutT = formatter.parse(formatter.format(((TacheLongCours) tache).getDateDebut()));
                    debut.setValue(formatter.format(((TacheLongCours) tache).getDateDebut()));
                    achevement.setText(String.valueOf(((TacheLongCours) tache).getAchevement()));
                } else {
                    debutT = formatter.parse(formatter.format(DateUtil.dateCourante()));
                }
                echeance.setValue(formatter.format(tache.getEcheance()));

            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(echeanceT);
            echeanceT = cal.getTime();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(debutT);
            debutT = cal2.getTime();
            /*********/


            valider = new JButton("Valider");
            valider.addActionListener(new ModifierTacheValidation());


            ArrayList<JPanel> panels = new ArrayList<>();
            valider.setPreferredSize(new Dimension(50, 20));
            pan.add(valider);
            //view.getContainer().updateUI();
            panels.add(pan);
            view.getContainer().setLayout(new GridLayout(0, 1));
            view.refreshjp(panels);
        }

        /**
         * Les modifications sont prises en charge après avoir cliquer sur "valider"
         */
        private class ModifierTacheValidation implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titreT = titre.getText();
                String categT = categories.getSelectedItem().toString();
                int importanceT=1;
                        try{
                            importanceT= Character.getNumericValue(importance.getText().charAt(0));
                        }catch (Exception e1)
                        {

                        }


                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date echeanceT = null, debutT = null;
                try {
                    echeanceT = formatter.parse(echeance.getText());
                    if (tache.getClass().getSimpleName().equals("TacheLongCours")) {
                        debutT = formatter.parse(debut.getText());
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(echeanceT);
                echeanceT = cal.getTime();
                if (tache.getClass().getSimpleName().equals("TacheLongCours")) {
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(debutT);
                    debutT = cal2.getTime();
                }


                if (!tache.estRealisee) {
                    tache.setTitre(titreT);
                    tache.setCategorie(categT);
                    tache.setEcheance(echeanceT);
                    tache.setNivImportance(importanceT);
                    if (tache.getClass().getSimpleName().equals("TacheLongCours")) {
                        ((TacheLongCours) tache).setDateDebut(debutT);
                        ((TacheLongCours) tache).setAchevement(Integer.parseInt(achevement.getText()));
                    } else {
                        if (estRealisee.isSelected()) {
                            tache.setTacheRealisee();
                        }
                    }
                }
                utilisateur.update();
                pagCour();

            }
        }
    }

    /**
     * Ecouteur gérant l'ajout de catégorie
     */
    class AjoutCategorieListener implements ActionListener {

        JLabel ajoutLabel = new JLabel("Ajouter une catégorie");
        JTextField categ = new JTextField();
        JButton valider = new JButton("Valider");

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel pan = new JPanel();
            ArrayList<JPanel> panels = new ArrayList<>();

            //pan.setLayout(new GridLayout(3, 1));
            categ.setPreferredSize(new Dimension(400, 40));
            pan.add(ajoutLabel);
            pan.add(categ);
            pan.add(valider);
            panels.add(pan);
            valider.addActionListener(new AjoutCategorieValidation());
            view.refreshjp(panels);

        }

        private class AjoutCategorieValidation implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String info = utilisateur.ajouterCat(categ.getText());
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, info, "Ajout catégorie", JOptionPane.INFORMATION_MESSAGE);
                pagCour();
            }
        }
    }

    /**
     * Ecouteur gérant la modification de catégorie
     */
    class ModifierCategorieListener implements ActionListener {


        ArrayList<JTextField> categ = new ArrayList<>();

        JButton valider = new JButton("Valider");


        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel pan = new JPanel();

            ArrayList<String> categories = utilisateur.getCategories().getLcategorie();
            view.getContainer().setLayout(new GridLayout(0, 1));
            ArrayList<JPanel> panels = new ArrayList<>();

            for (String categorie : categories) {
                JTextField field = new JTextField(categorie);
                field.setPreferredSize(new Dimension(400, 40));
                categ.add(field);
                pan.add(field);
            }

            pan.add(valider);
            valider.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<String> categories = utilisateur.getCategories().getLcategorie();
                    for (int i = 0; i < categ.size(); i++) {
                        JTextField categorie = categ.get(i);
                        String ancienne = utilisateur.getCategories().getLcategorie().get(i);
                        utilisateur.modifierCat(ancienne, categ.get(i).getText());
                    }
                    pagCour();
                }
            });
            panels.add(pan);

            view.refreshjp(panels);
        }
    }

    /**
     * Ecouteur gérant la suppression de catégorie
     */
    class SupprimerCategorieListener implements ActionListener {
        JComboBox categories = new JComboBox();
        JButton valider = new JButton("Valider");
        ArrayList<String> lCateg = utilisateur.getCategories().getLcategorie();


        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel pan = new JPanel();

            ArrayList<JPanel> panels = new ArrayList<>();

            for (String categorie : lCateg) {
                categories.addItem(categorie);
            }
            pan.add(categories);
            pan.add(valider);
            valider.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String categT = categories.getSelectedItem().toString();
                    String info = utilisateur.supprimerCat(categT);

                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, info, "Supprimer catégorie", JOptionPane.INFORMATION_MESSAGE);
                    pagCour();
                }
            });
            panels.add(pan);
            view.refreshjp(panels);
        }
    }

    /**
     * Initialise la vue en récupérant les composants graphiques créés à partir de la liste de tâches
     */
    private void initView() {
        ArrayList<JButton> buttons;

        buttons = getButtons(this.listeTachesModel.getlTaches());
        this.view = new TacheView(buttons, this);
    }

    /**
     * Crée des boutons à partir d'une liste de tâches
     *
     * @param taches: liste de tâches
     * @return les boutons créés
     */
    private ArrayList<JButton> getButtons(ArrayList<Tache> taches) {
        ArrayList<JButton> buttons = new ArrayList<>();
        //ArrayList<Tache> taches = L.getlTaches();
        for (Tache tache : taches) {
            Date date = tache.getEcheance();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateformatee = formatter.format(date);
            String dateIntFormatee = formatter.format(tache.getDateInt());
            String buttonText = "<html>" + tache.getTitre() + "<br/>" + "Echéance: " + dateformatee + "<br/>";
            if (tache.getClass().getSimpleName().equals("TacheLongCours")) {
                buttonText += "Echéance intermédiaire: " + dateIntFormatee;
            }
            buttonText += "</html>";
            JButton button = new JButton(buttonText);

            buttons.add(button);

        }
        return buttons;
    }


    /**
     * @param nb le nombre de page a afficher
     */
    public void setLenbTacheaAff(int nb)
    {
        this.lenbTacheaAff=nb;
    }

    public JPanel afficheBilan(Date datedeb, Date datefin) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        JPanel jpTARP = new JPanel();
        jpTARP.setLayout(new BoxLayout(jpTARP, BoxLayout.PAGE_AXIS));
        DefaultListModel tacheTitres = new DefaultListModel();


        listeTachesModel.bilan(datedeb, datefin);

        int tTDRPeriod = listeTachesModel.getTsDRealPer();
        int tTDARPeriod = listeTachesModel.getTsARealPer();
        int tTDRRPeriod = listeTachesModel.getTsDRRealPer();
        double pourcTRealPerio, pourcTRRealPerio;
        if (tTDARPeriod != 0) {
            pourcTRealPerio =(double) tTDRPeriod / tTDARPeriod * 100;
            pourcTRRealPerio = (double) tTDRRPeriod / tTDARPeriod * 100;
        } else {
            pourcTRealPerio = 0;
            pourcTRRealPerio = 0;
        }



        ArrayList<Tache> lTsARealPeriod = listeTachesModel.getlTachesARealiserPeriod();

        for (Tache t : lTsARealPeriod) {
            tacheTitres.addElement("Date EI=" + formatter.format(t.getDateInt()) + "   " + t.getTitre());
        }

        final JList titreList = new JList(tacheTitres);

        JLabel pourcTRealPerioLabel = new JLabel("Pourcentage de taches realisées sur cette periode " +
                pourcTRealPerio + "%");

        JLabel pourcTRRealPerioLabel = new JLabel("Pourcentage de taches realisées en retard sur cette periode " +
                pourcTRRealPerio + "%");
        titreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        titreList.setSelectedIndex(0);
        titreList.setVisibleRowCount(10);

        JScrollPane titreListScrollPane = new JScrollPane(titreList);

        jpTARP.add(titreListScrollPane);
        jpTARP.add(pourcTRealPerioLabel);
        jpTARP.add(pourcTRRealPerioLabel);


        return jpTARP;

    }

    /*
     * Affichage contrôlé par rapport au nombre de boutons affichés
     * 
     */
    public static int mod(int m, int n) {
        return m >= 0 ? m % n : (n - abs(m % n)) % n;
    }

    public void afficheViewContro(int i) {
        int nbTachesAff = 0;
        //System.out.println("coucou je boucle");
        ArrayList<JButton> buttons = new ArrayList<>();
        ListeTaches ltaches = listeTachesModel;
        ArrayList<JPanel> lpanels = new ArrayList<>();




        int mind;
        int indfin = ind;


        if (i < 0) {

            ind += 2 * i;
            indfin += i;

        } else if (i > 0) {
            indfin += i;

        } else {
            //i==0
            ind -=lenbTacheaAff ;

        }


        if(ind<0 || indfin<0)
        {
            ind =0 ;
            indfin=lenbTacheaAff;
        }

        System.out.println("ind=" + ind + " indfin=" + indfin + " ltaches.getnbTaches=" + ltaches.getnbTaches());
        System.out.println("le nombTachesAAF="+lenbTacheaAff);



        while (ind < indfin && (ind < ltaches.getnbTaches())) {
            if(ind%lenbTacheaAff==0)
            {
                derindModindAff=ind;
            }
            mind = ind;
            JPanel jp = new JPanel();
            JPanel jpbfbm = new JPanel(); //jpanel qui contiendra bf : bouton ferme , bm : bouton modifie


            jp.setLayout(new FlowLayout());

            jpbfbm.setPreferredSize(new Dimension(50, 100));
            jpbfbm.setLayout(new GridLayout(0, 1));
            Date date = ltaches.getTacheInd(mind).getEcheance();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateformatee = formatter.format(date);
            String dateIntFormatee = formatter.format(ltaches.getTacheInd(mind).getDateInt());

            String buttonText = "<html>" + ltaches.getTacheInd(mind).getTitre() + "<br/>" + "Echéance: " + dateformatee + "<br/>";
            if (ltaches.getTacheInd(mind).getClass().getSimpleName().equals("TacheLongCours")) {
                buttonText += "Echéance intermédiaire: " + dateIntFormatee;
            }
            buttonText += "</html>";
            JButton button = new JButton(buttonText);
            button.setPreferredSize(new Dimension(100, 128));
            button.addActionListener(new ModifierTacheListener(ltaches.getTacheInd(mind)));
            if (ltaches.getTacheInd(mind).estEnRetard() && ltaches.getTacheInd(mind).estRealisee) {
                button.setBackground(Color.ORANGE);
            } else if (ltaches.getTacheInd(mind).estEnRetard()) {
                button.setBackground(Color.RED);
            } else if (ltaches.getTacheInd(mind).estRealisee) {
                button.setBackground(Color.GREEN);
            }

            jp.add(button);

            /*
             * On cree par la suite un boutton pour fermer la tache
             */
            int mindf = mind;


            JButton boutonfermer = new JButton();
            boutonfermer.setPreferredSize(new Dimension(140, 100));

            boutonfermer.setForeground(Color.RED);
            boutonfermer.setText("X");
            boutonfermer.setFont(new Font("Arial", Font.BOLD, 14));
            boutonfermer.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    utilisateur.supprimerTacheInd(mindf);
                    evenDel=true;
                    pagCour();
                    evenDel=false;
                }

            });
            long d = DateUtil.getDateDiff(DateUtil.dateCourante(), ltaches.getTacheInd(mind).getEcheance(), TimeUnit.DAYS);
            String str = d < 0 ? "+" + (-1) * d : "-" + d;
            JLabel joursRestants = new JLabel("J " + str);
            joursRestants.setForeground(Color.BLUE);
            if (d < 0) {
                joursRestants.setForeground(Color.RED);
            }
            if (!ltaches.getTacheInd(mind).estRealisee) {
                jpbfbm.add(joursRestants);

            }
            jpbfbm.add(boutonfermer);

            jp.add(jpbfbm);

            lpanels.add(jp);
            ind++;
            nbTachesAff++;
           // System.out.println("indCour="+ind);
        }
        if( ind > 0 && nbTachesAff<lenbTacheaAff && nbTachesAff!=0)
        {
            ind+=(lenbTacheaAff-nbTachesAff);

        }

        System.out.println("FinindReel=" + ind + " FinindFinReel=" + indfin + "\n");
        if(nbTachesAff>0 || evenDel)
        {

            if(evenDel && nbTachesAff==0 && ltaches.getnbTaches()>0) {
                pagCour();
                return ;
            }
            this.view.refreshjp(lpanels);
        }

    }

    /**
     * Reinitialise l'affichage et on se place a la premiere
     * page de l'affichage.
     */
    public void pagReset()
    {

        ind=0;
        derindModindAff=0;
        pagCour();
    }

    /**
     * Page courante
     */
    public void pagCour() {
        listeTachesModel.tachesdRealiseTotal();
        afficheViewContro(0);
    }

    /**
     * Page suivante
     */
    public void pagSuiv() {
        listeTachesModel.tachesdRealiseTotal();
        afficheViewContro(lenbTacheaAff);

    }

    /**
     * Page précédente
     */
    public void pagPrec() {
        listeTachesModel.tachesdRealiseTotal();
        afficheViewContro(-1*lenbTacheaAff);
    }


    public static void main(String[] args) {

        Utilisateur u1 = new Utilisateur();

        ListeTaches listeTaches = new ListeTaches();
       /* Date dcour = DateUtil.dateCourante();

        TacheLongCours tlc1 = new TacheLongCours("tlc1", DateUtil.ajoutJours(dcour, 30), dcour, 3, "Personnel");
        TacheLongCours tlc2 = new TacheLongCours("tlc2", DateUtil.ajoutJours(dcour, 15), dcour, 3, "Personnel");
        TacheLongCours tlc3 = new TacheLongCours("tlc3", DateUtil.ajoutJours(dcour, 10), dcour, 1, "Travail");
        TacheLongCours tlc4 = new TacheLongCours("tlc4", DateUtil.ajoutJours(dcour, 13), dcour, 3, "Personnel");
        TacheLongCours tlc5 = new TacheLongCours("tlc5", DateUtil.ajoutJours(dcour, 50), dcour, 1, "Travail");
        TacheLongCours tlc6 = new TacheLongCours("tlc6", DateUtil.ajoutJours(dcour, 30), dcour, 2, "Travail");
        TacheLongCours tlc7 = new TacheLongCours("tlc7", DateUtil.ajoutJours(dcour, 15), dcour, 1, "Travail");
        TacheLongCours tlc8 = new TacheLongCours("tlc8", DateUtil.ajoutJours(dcour, 10), dcour, 2, "Travail");
        TacheLongCours tlc9 = new TacheLongCours("tlc9", DateUtil.ajoutJours(dcour, 13), dcour, 1, "Personnel");
        TachePonctuelle tp1 = new TachePonctuelle("tp1", DateUtil.ajoutJours(dcour, 2), 1, "Personnel");
        TachePonctuelle tp2 = new TachePonctuelle("tp2", DateUtil.ajoutJours(dcour, 4), 3, "Personnel");
        TachePonctuelle tp3 = new TachePonctuelle("tp3", DateUtil.ajoutJours(dcour, 9), 2, "Travail");
        TachePonctuelle tp4 = new TachePonctuelle("tp4", DateUtil.ajoutJours(dcour, 5), 3, "Personnel");
        TachePonctuelle tp5 = new TachePonctuelle("tp5", DateUtil.ajoutJours(dcour, -100), 1, "Personnel");

        u1.ajouteTache(tlc2);
        u1.ajouteTache(tlc3);
        u1.ajouteTache(tlc4);
        u1.ajouteTache(tlc5);
        u1.ajouteTache(tlc6);
        u1.ajouteTache(tlc7);
        u1.ajouteTache(tlc8);
        u1.ajouteTache(tlc9);
        u1.ajouteTache(tp1);
        u1.ajouteTache(tp2);
        u1.ajouteTache(tp3);
        u1.ajouteTache(tp4);
        u1.ajouteTache(tp5);


        TacheLongCours tlcretard = new TacheLongCours("tlc1", DateUtil.ajoutJours(DateUtil.createDate(05,07,2015), 30), DateUtil.createDate(05,07,2015), 3, "Personnel");

        u1.ajouteTache(tlcretard);*/
        TacheController tacheController = new TacheController(u1);

    }
}
