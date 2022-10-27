/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;



/**Customer class model. */
public class Customer {

    /**customer ID. */
    private int customerID;
    /**customer name. */
    private String customerName;
    /**customer address. */
    private String address;
    /**customer postal code. */
    private String postalCode;
    /**customer phone. */
    private String phone;
    /**user who created the customer. */
    private String createdBy;
    /**user who lasted edited the customer. */
    private String lastUpdateBy;

    //** Customer division. Used to set combo box value when editing a customer in the EditCustomerScreen Controller. */
    private Division division;
    //** Customer country. Used to set combo box value when editing a customer in the EditCustomerScreen Controller. */
    private Country country;

    /**Constructor for the Customer class
     * @param customerID customer ID
     * @param customerName customer Name
     * @param address customer Address
     * @param postalCode customer postal code
     * @param phone customer phone
     * @param createdBy current user
     * @param lastUpdateBy current user
     * @param divisionID customer division ID
     * @param countryID customer country ID
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone,
                    String createdBy, String lastUpdateBy, int divisionID, int countryID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;

        // assign the customer division and country objects based on the customer and division IDs
        for (Country c: Schedule.getAllCountries()){
            if(countryID==c.getCountryID()){
                this.country = c;
                for (Division d: c.getDivisions()){
                    if(divisionID == d.getDivisionID()){
                        this.division = d;
                    }
                }
            }
        }

    }
    /**Overridden toString() method for combo box display.
     * @return a string to display the customer name in combo boxes.
     */
    @Override
    public String toString(){
        return customerName;
    }

    /**Getter method for customer ID
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** Setter for Customer ID
     * @param customerID customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**Getter method for customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }
    /**Getter method for customer address
     * @return customer address
     */
    public String getAddress() {
        return address;
    }
    /**Getter method for customer postal code
     * @return customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**Getter method for customer phone
     * @return customer phone
     */
    public String getPhone() {
        return phone;
    }
    /**Getter method for customer division
     * @return customer division
     */
    public Division getDivision() {
        return division;
    }
    /**Getter method for customer country
     * @return customer country
     */
    public Country getCountry() {
        return country;
    }

    /**Getter for division name.
     * @return division name.
     */
    public String getDivisionName() {
        return  division.getDivisionName();
    }

    /**Getter for country name.
     * @return country name.
     */
    public String getCountryName() {
        return country.getCountryName();
    }
}
