package pjavatest;

import org.junit.Test;

import pjava.DateUtil;
import pjava.Tache;
import pjava.TachePonctuelle;



import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;


public class TachePonctuelleTest {
	@Test
	public void echeanceTest() throws Exception {
		Tache t1 = new TachePonctuelle("tache",DateUtil.dateCourante(),1);
		assertEquals(DateUtil.dateCourante(), t1.getEcheance());

	}

	@Test
	public void estEnRetardTest()  {
		Tache t1 = new TachePonctuelle("t1",26,10,2016,2);
		assertTrue(t1.estEnRetard());
	}
}