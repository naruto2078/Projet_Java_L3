package pjavatest;

import org.junit.Test;


import pjava.DateUtil;
import pjava.ListeTaches;
import pjava.TacheLongCours;
import pjava.TachePonctuelle;


import static org.junit.Assert.assertEquals;

import java.util.Date;

public class ListeTachesTest {

    Date dcour = DateUtil.dateCourante();
    TacheLongCours tlc1 = new TacheLongCours("tlc1", DateUtil.ajoutJours(dcour, 30), dcour, 3, "Personnel");
    TacheLongCours tlc2 = new TacheLongCours("tlc2", DateUtil.ajoutJours(dcour, 15), dcour, 1, "Personnel");
    TacheLongCours tlc3 = new TacheLongCours("tlc3", DateUtil.ajoutJours(dcour, 10), dcour, 1, "Travail");
    TacheLongCours tlc4 = new TacheLongCours("tlc4", DateUtil.ajoutJours(dcour, 13), dcour, 2, "Personnel");
    TacheLongCours tlc5 = new TacheLongCours("tlc5", DateUtil.ajoutJours(dcour, 50), dcour, 1, "Travail");
    TacheLongCours tlc6 = new TacheLongCours("tlc6", DateUtil.ajoutJours(dcour, 30), dcour, 2, "Travail");

    ListeTaches l = new ListeTaches();


    @org.junit.Test
    public void createTest() {
        TachePonctuelle t1 = new TachePonctuelle("t1", 26, 10, 2016, 1);
        TacheLongCours t2 = new TacheLongCours("t2", 29, 10, 2016, 25, 10, 2016, 1);
        ListeTaches l = new ListeTaches();
        l.ajouteTache(t1);
        l.ajouteTache(t2);
        //on vérifie que les tâches soient bien ajoutées à la liste de tâches
        assertEquals(2, l.getnbTaches());
        assertEquals(t1, l.getTacheNom("t1"));
    }

    @org.junit.Test
    public void triEcheanceTest() {
       l.ajouteTache(tlc6);
        l.ajouteTache(tlc5);
        l.ajouteTache(tlc4);
        l.ajouteTache(tlc3);
        l.ajouteTache(tlc2);
        l.ajouteTache(tlc1);
        l.triTachesEcheance();

        //tlc3 est la tâche ayant la date d'échéance la plus proche
        assertEquals(tlc3, l.getTacheInd(0));

    }

    @Test
    public void triIntermediaireTest() {
        l.ajouteTache(tlc6);
        l.ajouteTache(tlc5);
        l.ajouteTache(tlc4);
        l.ajouteTache(tlc3);
        l.ajouteTache(tlc2);
        l.ajouteTache(tlc1);
        tlc4.setAchevement(80);
        l.triEcheanceInt();
        //on teste que tlc4 soit bien la dernière de la collection après le tri car elle a l'échéance intermédiaire la plus grande
        assertEquals(tlc4, l.getTacheInd(5));

    }
}
