package pjava;

import java.util.Comparator;

public class ComparatorEcheance implements Comparator<Tache>
{
	public int compare(Tache t1, Tache t2)
	{

		 if(t1.getEcheance().before(t2.getEcheance()))
		 	return -1;
		 	
		 if(t1.getEcheance().after(t2.getEcheance()))
			return 1;
		 
		 else 
			 return 0;
	}

}
