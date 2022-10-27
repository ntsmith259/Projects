package ReportObjects;

/**Object that is used for generating the Appointment report.
 * This report displays the number of each type of appointment for each month.
 */
public class AppointmentReport {

    /**Appointment type. */
    private String type;
    /**Appointment month. */
    private String month;
    /**Appointment year. */
    private String year;
    /**Total number of appointments of the type and month in the database. . */
    private int total;

    /**Constructor for the AppointmentReport class.
     * @param type appointment type.
     * @param month appointment month
     * @param year appointment year
     * @param total number of appointment for the type and month of the object
     */
    public AppointmentReport(String type, String month, String year, int total) {
        this.type = type;
        this.month = month;
        this.year = year;
        this.total = total;
    }

    /**Getter for appointment type.
     * @return appointment type.
     */
    public String getType() {
        return type;
    }

    /**Setter for appointment type.
     * @param type appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }
    /**Getter for appointment month.
     * @return appointment month.
     */
    public String getMonth() {
        return month;
    }
    /**Setter for appointment month.
     * @param month appointment month.
     */
    public void setMonth(String month) {
        this.month = month;
    }
    /**Getter for appointment year.
     * @return appointment year.
     */
    public String getYear() {
        return year;
    }
    /**Setter for appointment year.
     * @param year appointment year.
     */
    public void setYear(String year) {
        this.year = year;
    }
    /**Getter for appointment total.
     * @return appointment total.
     */
    public int getTotal() {
        return total;
    }
    /**Setter for appointment total.
     * @param total appointment total.
     */
    public void setCount(int total) {
        this.total = total;
    }
}
