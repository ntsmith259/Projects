/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**Technician class model. */
public class Technician {

    /**Technician ID. */
    private int techID;
    /**Technician Name. */
    private String techName;

    /**Constructor for the Technician Class.
     * @param techID the tech ID
     * @param techName the tech Name.
     */
    public Technician(int techID, String techName) {
        this.techID = techID;
        this.techName = techName;
    }

    /**Overridden toString() method for combo box display.
     * @return a string to display the tech name in combo boxes.
     */
    @Override
    public String toString(){
        return techName;
    }

    /**Getter method for contact ID.
     * @return contact ID.
     */
    public int getTechID() {
        return techID;
    }

    /**Getter method for contact name.
     * @return contact name.
     */
    public String getTechName() {
        return techName;
    }


}
