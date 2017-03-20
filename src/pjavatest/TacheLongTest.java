package pjavatest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import pjava.DateUtil;
import pjava.TacheLongCours;


public class TacheLongTest {

	@Test
	public void testEstEnRetard()  {
		
		TacheLongCours t1 = new TacheLongCours("t1",29,10,2016, 25,10,2016,1);
		t1.setAchevement(50);
		long d = DateUtil.getDateDiff(t1.getDateDebut(), DateUtil.dateCourante(), TimeUnit.DAYS);
		//System.out.println(d);
		assertTrue(t1.estEnRetard()); 
		Date curr = DateUtil.dateCourante();
	}

}
