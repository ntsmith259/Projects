/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**Division class model. */
public class Division {

    /**Division ID. */
    private int divisionID;
    /**Division name. */
    private String divisionName;

    /**Constructor for Division class.
     * @param divisionID division ID
     * @param divisionName division name
     */
    public Division(int divisionID, String divisionName) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    /**Overridden toString() method for combo box display.
     * @return a string to display the divison name in combo boxes.
     */
    @Override
    public String toString(){
        return divisionName;
    }

    /**Getter method for division ID.
     * @return division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**Getter method for division name.
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }


}
