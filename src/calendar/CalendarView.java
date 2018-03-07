package calendar;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Zachary 
 * 	View Gets input from user and passes is to controller when
 *  requested Prints directly to console any output the controller wants
 *  to display
 */
public class CalendarView
{
	// scanner for input
	public Scanner scanner = new Scanner(System.in);
	// constructor
	public CalendarView(){}
	
	
	/** abbreviated System.out.print
	 * @param s
	 */
	private void sop(Object s)
	{
		System.out.print(s);
	}

	/** abbreviated System.out.println
	 * @param s
	 */
	private void sopl(Object s)
	{
		System.out.println(s);
	}

	/** prints message to console output
	 * @param msg
	 */
	public void showMessage(String msg)
	{
		sop(msg);
	}

	/** prints message to console output with new line
	 * @param msg
	 */
	public void showMessageNL(String msg)
	{
		sopl(msg);
	}

	/** gets input from user
	 * @return input
	 */
	public Object getInput()
	{
		return scanner.nextLine();
	}
	

	/**
	 * Displays one month on a calendar to console in typical calendar format
	 * Days with reminders are marked with an asterisk.
	 * Today is surrounded with brackets.
	 * 
	 * @param ldt 				- LocalDateTime representing whatever month you want to print
	 * @param daysWithReminders - list of days that contain a reminder. 
	 */
	public void displayCalendar(LocalDateTime ldt, ArrayList<Integer> daysWithReminders)
	{
		// format for month names
		DateTimeFormatter ThreeLetterMonth = DateTimeFormatter.ofPattern("MMM");
		DateTimeFormatter FourLetterMonth = DateTimeFormatter.ofPattern("MMMM");
		Month thisMonth = Month.of(ldt.getMonthValue());
		int temp = ldt.getMonthValue()+1;
		if (temp > 12) { temp = 1; } // make sure next isn't out of bounds
		Month next = Month.of(temp);
		temp = ldt.getMonthValue()-1;
		if (temp <= 0) { temp = 12; } // make sure prev isn't out of bounds
		Month previous = Month.of(temp);
		
		// print previous and next month names
		sopl("\n < " + ThreeLetterMonth.format(previous) + "\t\t\t    " +  ThreeLetterMonth.format(next) + " >\n");
		// print current month name and year
		sopl(" " + FourLetterMonth.format(thisMonth) + " " + ldt.getYear());
		// print month names
		sopl(" Su   Mo   Tu   We   Th   Fr   Sa");

		// print correct spacing before first day
		int firstDayOfMonth = (ldt.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek().getValue())%7;
		for (int i = 0; i < firstDayOfMonth; ++i)
		{
			sop("     "); // 5 spaces
		}

		int i = firstDayOfMonth; // offset based on where in the week the first day falls
		int day = 1;
		int lastDay = ldt.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

		while (day <= lastDay)
		{
			if (!today(day, ldt))
				sop(" "); // space for alignment
			while (i < 7 && day <= lastDay)
			{
				String dayStr = day + "";
				// add asterisk if day has reminder.
				if (daysWithReminders.contains(day))
				{
					dayStr = day + "*";
				}
				// bracket day if it equals current day
				if (today(day, ldt))
				{
					sop(String.format("%1$-6s", "[" + dayStr + "]"));
				}
				// one less space if day == tomorrow
				else if (tomorrow(day, ldt))
				{
					sop(String.format("%1$-4s", dayStr));
				} else
				{
					sop(String.format("%1$-5s", dayStr));
				}
				day++;
				i++;
			}
			sopl("");
			i = 0;
		}

	}

	/**
	 * Helper function for displayCalendar returns true if the day is today
	 * @param day
	 * @param ldt
	 */
	private boolean today(int day, LocalDateTime ldt)
	{
		// calendar for current date
		LocalDateTime now = LocalDateTime.now();
		// return true if day, month and year are the same
		return (day == now.getDayOfMonth() 
				&& ldt.getMonthValue() == now.getMonthValue()
				&& ldt.getYear() == now.getYear());

	}

	/**
	 * Helper function for displayCalendar returns true if the day is tomorrow
	 * @param day
	 * @param ldt
	 */
	private boolean tomorrow(int day,  LocalDateTime ldt)
	{
		return today(day + 1, ldt);
	}

}
