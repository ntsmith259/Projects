package utils;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/** Interface to check customer for null fields and conflicting times for customers.
 * Implemented by the Add and Edit CustomerControllers. */
public interface CustomerChecker {


    /**Checks the customer for null fields before allowing a new customer to be created or edited.
     * @param customerName customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone
     * @param division customer division
     * @param country customer country
     * @return list of null fields
     * @throws IllegalArgumentException
     */
    default String checkCustomerFields(String customerName, String address, String postalCode, String phone, Division division, Country country) throws IllegalArgumentException {
        StringBuilder warningMessage = new StringBuilder().append("\n");

        if (customerName.equals("")) {
            warningMessage.append("Customer Name " + "\n");
        }

        if (address.equals("")) {
            warningMessage.append("Customer Address " + "\n");
        }

        if (postalCode.equals("")) {
            warningMessage.append("Postal Code " + "\n");
            ;
        }

        if (phone.equals("")) {
            warningMessage.append("Phone Number " + "\n");
        }

        if (division == null) {
            warningMessage.append("Divison " + "\n");
        }

        if (country == null) {
            warningMessage.append("Country " + "\n");
        }
        return warningMessage.toString();

    }




}
