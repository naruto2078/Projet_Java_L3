package pjava;

import java.util.Comparator;

/**
 * Compare les tâches par date intermédiaire d'échéance
 * @author Raoul
 *
 */
public class ComparateurDateInt implements Comparator<Tache> {

	@Override
	public int compare(Tache t1, Tache t2) {

		if(t1.getDateInt().before(t2.getDateInt()))
			return -1;

		if(t1.getDateInt().after(t2.getDateInt()))
			return 1;

		else
			return 0;
	}
	

}
