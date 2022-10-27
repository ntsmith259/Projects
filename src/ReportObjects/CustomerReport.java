package ReportObjects;

/**Class that contains data on the number of appointments between contact and customer pairs. */
public class CustomerReport {


    /**
     * Technician ID.
     */
    private int techID;
    /**
     * Technician name.
     */
    private String techName;
    /**
     * Customer Name.
     */
    private String customerName;
    /**
     * Number of appointments for a contact/customer pair.
     */
    private int appointmentNum;

    /**
     * Constructor for the CustomerReport class.
     *
     * @param techID      tech ID
     * @param techName    contact Name
     * @param customerName   customer name
     * @param appointmentNum number of appointments for a contact/customer pair
     */
    public CustomerReport(int techID, String techName, String customerName, int appointmentNum) {
        this.techID = techID;
        this.techName = techName;
        this.customerName = customerName;
        this.appointmentNum = appointmentNum;

    }

    /**
     * Getter for contact name used to populate the Customer report table.
     *
     * @return contact name
     */
    public String getTechName() {
        return techName;
    }

    /**
     * Getter for customer name used to populate the Customer report table.
     *
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Getter for appointment number used to populate the Customer report table.
     *
     * @return appointment number
     */
    public int getAppointmentNum() {
        return appointmentNum;
    }

    /**
     * Getter for tech ID used to populate the Customer report table.
     *
     * @return tech ID
     */
    public int getTechID() {
        return techID;
    }
}


