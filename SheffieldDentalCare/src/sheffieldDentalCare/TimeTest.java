package sheffieldDentalCare;

import java.util.Arrays;
import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

public class TimeTest {

	public static void main(String[] args) throws ParseException {
		// Sorting strings of times
		List<String> times = Arrays.asList(new String[]{"17:00","15:30","12:15"});
		Collections.sort(times);
		System.out.println(times);
		
		// Set time 1
		String strTime1 = "14:50";
		Date time1 = new SimpleDateFormat("H:mm").parse(strTime1);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(time1);
		
		// Set time 2
		String strTime2 = "14:10";
		Date time2 = new SimpleDateFormat("H:mm").parse(strTime2);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(time2);
		
		// Check if time 2 is before time 1
		// Should be true as 14:10 is before 14:50
		System.out.println(cal2.before(cal1));
		// Check if time 1 is before time 2
		// Should be false as 14:10 is not before 14:50
		System.out.println(cal1.before(cal2));
		
		// Adding minutes to a time
		SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
		// Add 20 minutes to 14:50
		cal1.add(Calendar.MINUTE, 20);
		String newTime = sdf.format(cal1.getTime());
		System.out.println(newTime);
	}

}
