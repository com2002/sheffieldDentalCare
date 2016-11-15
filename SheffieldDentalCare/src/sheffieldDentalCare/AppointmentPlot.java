package sheffieldDentalCare;

public class AppointmentPlot extends Object {
	public String DATE;
	public int ENDTIME;
	public int STARTTIME;
	public int PATIENTID;
	public int APPOINTMENTID;
	
	/**
	 * <b>AppointmentPlot.DATE</b> returns date of appointment as a String format 'YYYY-MM-DD' <P>
	 * <b>AppointmentPlot.APPOINTMENTID</b> returns the appointment's ID as an int <P>
	 * <b>AppointmentPlot.PATIENTID</b> returns the patient's ID as an int <P>
	 * <b>AppointmentPlot.STARTTIME</b> returns the appointment's start time as an int in the form HHMM (or HMM if before 10am) <P>
	 * <b>AppointmentPlot.ENDTIME</b> retusn the appiontments end time as an int in the form HHMM (or HMM if before 10am) <P>
	 * @param date
	 * @param appointmentID
	 * @param patientID
	 * @param startTime
	 * @param endTime
	 */
	public AppointmentPlot(String date, int appointmentID, int patientID, String startTime, String endTime) {
		//remove seconds
		if (startTime.length() > 5) startTime = startTime.substring(0, startTime.length()-3);
		if (endTime.length() > 5) endTime = endTime.substring(0, endTime.length()-3);
		
		this.DATE = date;
		this.APPOINTMENTID = appointmentID;
		this.PATIENTID = patientID;
		//convert times to ints of the form HHMM
		this.STARTTIME = Integer.parseInt((startTime.replaceAll("[^\\d.]", "")));
		this.ENDTIME = Integer.parseInt((endTime.replaceAll("[^\\d.]", "")));

		}	
}
