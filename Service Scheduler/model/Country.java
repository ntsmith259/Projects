/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.ObservableList;

/**Country alass model.  */
public class Country {
    /**Country ID.  */
    private int countryID;
    /**Country name.  */
    private String countryName;
    /**List of divisions within the country.  */
    private ObservableList<Division> divisions;

    /**Constructor for the Country Class.
     * @param countryID country ID.
     * @param countryName country name.
     * @param divisions List of divisions within the country.
     */
    public Country(int countryID, String countryName, ObservableList<Division> divisions) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.divisions = divisions;
    }

    /**Overridden toString() method for combo box display.
     * @return a string to display the country name in combo boxes.
     */
    @Override
    public String toString(){
        return countryName;
    }

    /**Getter method for country ID.
     * @return country ID.
     */
    public int getCountryID() {return countryID;   }

    /**Getter method for country name.
     * @return country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**Getter method for the division list.
     * @return division list.
     */
    public ObservableList<Division> getDivisions() {
        return divisions;
    }




}
