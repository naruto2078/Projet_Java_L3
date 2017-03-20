package pjava;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Classe facilitant la création d'une Date.
 * @author Raoul
 */
 

public class DateUtil {
	public static Date createDate(int date, int month, int year){
		GregorianCalendar calendar= new GregorianCalendar();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, date);

		return calendar.getTime();
	}

	public static Date ajoutJours(Date date, int jours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, jours);
		return cal.getTime();
	}

	/**
	 * @return la date courante
	 */
	public static Date dateCourante() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	/**
	 * Obtenir la différence entre deux dates
	 * @param date1 l'ancienne date
	 * @param date2 la nouvelle  date
	 * @param timeUnit l'unité dans laquelle calculer la différence (minute, heure, jour, etc)
	 * @return la différence dans l'unité spécifiée
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}

}
