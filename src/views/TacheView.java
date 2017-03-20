package views;


import controller.TacheController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

/**
 * Classe représentant la vue: les éléments swing
 */
@SuppressWarnings("unused")

public class TacheView extends JFrame {
    private JPanel container = new JPanel();
    private JPanel topcontainer = new JPanel();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<JPanel> panels = new ArrayList<>();
    private TacheController tc;


    /**
     * Chaque bouton est contenu dans une JPanel
     */
    private ArrayList<JPanel> lcontainers = new ArrayList<>();

    //Le menu de notre application
    private JMenuBar menuBar = new JMenuBar();
    private JMenu tacheMenu = new JMenu("Tâche");
    private JMenu categorieMenu = new JMenu("Catégorie");

    public JMenu getStatsMenu() {
        return statsMenu;
    }

    private JMenu statsMenu = new JMenu("Statistiques");
    private JMenu session = new JMenu("Session");
    private JMenu tri = new JMenu("Trier");

    private JMenuItem ajouterItem = new JMenuItem("Ajouter");
    private JMenuItem supprimerItem = new JMenuItem("Supprimer");
    private JMenuItem quitterItem = new JMenuItem("Quitter");

    private JMenuItem ajoutCatItem = new JMenuItem("Ajouter");
    private JMenuItem modifierCatItem = new JMenuItem("Modifier");
    private JMenuItem supprimerCatItem = new JMenuItem("Supprimer");

    private JMenuItem triDateEchItem = new JMenuItem("par date d'échéance");
    private JMenuItem triDateIntItem = new JMenuItem("par date intermédiaire");


    private JMenuItem tri8Item = new JMenuItem("Autre tri");

    private JMenuItem bilanItem = new JMenuItem("Generer bilan");

    public JMenuItem getAjouterItem() {
        return ajouterItem;
    }

    public JMenuItem getTriDateEchItem() {
        return triDateEchItem;
    }

    public JMenuItem getTriDateIntItem() {
        return triDateIntItem;
    }

    public JMenuItem getTri8Item() {
        return tri8Item;
    }

    public JMenuItem getBilanItem() {
        return bilanItem;

    }

    /**
     * @param buttons: les boutons représentent chacun une tâche de l'utilisateur
     * @param tc:      Une instance du contrôleur
     */
    public TacheView(ArrayList<JButton> buttons, TacheController tc) {
        this.tc = tc;
        //this.setButtons(buttons);

        this.setTitle("todoList");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        topcontainer.setLayout(new FlowLayout());
        topcontainer.setPreferredSize(new Dimension(100, 50));

        JButton buttontopsuiv = new JButton(">");
        buttontopsuiv.setPreferredSize(new Dimension(50, 50));
        buttontopsuiv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("je boucle ici");
                tc.pagSuiv();
            }
        });

        JButton buttontopprec = new JButton("<");
        buttontopprec.setPreferredSize(new Dimension(50, 50));
        buttontopprec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("je boucle ici");
                tc.pagPrec();
            }
        });

        topcontainer.add(buttontopprec);
        topcontainer.add(buttontopsuiv);


        this.add(topcontainer, "North");

        container.setLayout(new GridLayout(3, 3));
        this.setPreferredSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(800,600));
        //initialiser le menu
        tacheMenu.add(ajouterItem);
        tacheMenu.addSeparator();
        tacheMenu.add(supprimerItem);

        categorieMenu.add(ajoutCatItem);
        categorieMenu.add(modifierCatItem);
        categorieMenu.add(supprimerCatItem);

        session.add(quitterItem);

        tri.add(triDateEchItem);
        tri.add(triDateIntItem);
        tri.add(tri8Item);
        tacheMenu.add(tri);
        statsMenu.add(bilanItem);
        menuBar.add(tacheMenu);
        menuBar.add(statsMenu);
        menuBar.add(categorieMenu);
        menuBar.add(session);

        quitterItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuBar.add(tacheMenu);
        menuBar.add(statsMenu);
        menuBar.add(session);

        this.setJMenuBar(menuBar);
        this.add(container, "Center");
        this.setVisible(true);

    }

    public void refresh(ArrayList<JButton> buttons) {

        setButtons(buttons);

        container.updateUI();
    }

    /**
     * Cette méthode met à jour le contenu de la fenêtre
     * en remplaçant le contenu actuel par un autre
     *
     * @param panels: liste de panels
     */
    public void refreshjp(ArrayList<JPanel> panels) {
        setPanels(panels);

        container.updateUI();
    }

    public JPanel getContainer() {
        return container;
    }

    public void setContainer(JPanel container) {
        this.container = container;
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public void setPanels(ArrayList<JPanel> panels) {
        // System.out.println("je setPanels now now");
        /*if (panels.isEmpty()) {
            System.out.println("je suis viiiiiide");
        }

        for (JPanel panel : this.panels) {
            container.remove(panel);
        }

        this.panels = panels;
        for (JPanel panel : this.panels) {
            // System.out.println("je boucle now now");
            container.add(panel);
        }*/
        container.removeAll();
        this.panels = panels;

        if (panels.size() == 1) {
            container.setLayout(new GridLayout(0, 1));
        } else {
            container.setLayout(new GridLayout(3, 3));
        }
        for (JPanel panel : this.panels) {
            container.add(panel);
        }
    }

    public void setButtons(ArrayList<JButton> buttons) {
        for (JButton bouton : this.buttons) {
            container.remove(bouton);
        }

        this.buttons = buttons;
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(50, 50));
            container.add(button);
        }

    }

    /**
     * @param button: bouton à ajouter à la liste de boutons
     */
    public void addButton(JButton button) {


        button.setPreferredSize(new Dimension(50, 50));
        container.add(button, 0);

        container.updateUI();
    }

    /**
     * Supprime un bouton de la fenêtre
     */
    public void removeButtons() {
        for (JButton bouton : this.buttons) {
            container.remove(bouton);
        }
    }


    /*Accesseurs sur les différents éléments de la fenêtre.
    Cela sera utile pour ajouter des écouteurs d'évènements par la suite dans le contrôleur*/
    public JMenuItem getAjoutCatItem() {
        return ajoutCatItem;
    }

    public void setAjoutCatItem(JMenuItem ajoutCatItem) {
        this.ajoutCatItem = ajoutCatItem;
    }

    public JMenuItem getModifierCatItem() {
        return modifierCatItem;
    }

    public void setModifierCatItem(JMenuItem modifierCatItem) {
        this.modifierCatItem = modifierCatItem;
    }

    public JMenuItem getSupprimerCatItem() {
        return supprimerCatItem;
    }

    public void setSupprimerCatItem(JMenuItem supprimerCatItem) {
        this.supprimerCatItem = supprimerCatItem;
    }
}
