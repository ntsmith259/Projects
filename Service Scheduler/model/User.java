/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/** User class model. */
public class User {

    /** The User ID. */
    private int userID;
    /** The User username. */
    private String userName;
    /** The User password. */
    private String password;

    /**Constructor for the User Class.
     * @param userID userID
     * @param userName user name
     * @param password user password
     */
    public User(int userID,String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
    }
    /**Overridden toString() method for combo box display.
     * @return a string to display the user name in combo boxes.
     */
    @Override
    public String toString(){
        return userName;
    }


    /**Getter for the user ID.
     * @return user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**Getter for the user name.
     * @return user name.
     */
    public String getUserName() {
        return userName;
    }

    /**Getter for the password.
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }


}
