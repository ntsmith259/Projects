package utils;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/** Interface to check appointment for null fields and conflicting times for the selected customer.
 * Implemented by the Add and Edit AppointmentControllers. */
public interface AppointmentChecker {

    /** Checks appointment fields for null values.
     * @param title appointment title
     * @param type appointment type
     * @param desc appointment description
     * @param location appointment location
     * @param customer appointment customer
     * @param user appointment user
     * @param technician appointment technician
     * @param startDate appointment start date
     * @param startTime appointment start time
     * @param endDate appointment end date
     * @param endTime appointment end time
     * @return list of null fields
     *  @throws IllegalArgumentException
     */
    default String checkAppointmentFields(String title, String type, String desc, String location, Customer customer,
                                          User user, Technician technician, LocalDate startDate, String startTime,
                                          LocalDate endDate, String endTime) throws IllegalArgumentException {

        StringBuilder warningMessage = new StringBuilder().append("\n");

        if (title.equals("")) {warningMessage.append("Title " + "\n");}
        if (type.equals("")) {warningMessage.append("Type " + "\n");}
        if (desc.equals("")) {warningMessage.append("Description " + "\n");}

        if (location.equals("")) {
            warningMessage.append("Location " + "\n");
        }

        if (customer  == null) {
            warningMessage.append("Customer " + "\n");
        }

        if (user == null) {
            warningMessage.append("User " + "\n");
        }

        if (technician == null) {
            warningMessage.append("Technician " + "\n");
        }

        if (startDate == null) {
            warningMessage.append("Start Date " + "\n");
        }

        if (startTime == null) {
            warningMessage.append("Start Time " + "\n");
        }

        if (endDate == null) {
            warningMessage.append("End Date " + "\n");
        }

        if (endTime == null) {
            warningMessage.append("End Time " + "\n");
        }
        return warningMessage.toString();
    }

    /**Checks appointment for conflicting times based on the customer time zone and the company office hours.
     * AppointmentID is used by the EditAppointmentController to avoid checking the appointment against itself.
     * A negative value is passed when creating an appointment as a simple way to avoid finding
     * a conflicting appointment. Contains lambdas checkStart, checkEnd and checkOverlap a for loop that checks for overlaps.
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param customerID customer ID
     * @param appointmentID appointment ID
     * @return an error message based on the type of conflict
     * @throws IllegalArgumentException
     */
    default String checkAppointmentTimes(LocalDateTime start, LocalDateTime end, int customerID, int appointmentID) throws IllegalArgumentException{
        StringBuilder warningMessage = new StringBuilder().append("\n");
        boolean needsAlert = false;

        //determines if meeting start is after the end
        if (start.isEqual(end) || start.isAfter(end)){
            warningMessage.append("The start time for the meeting must be scheduled before the end of the meeting." + "\n");
            needsAlert = true;
        }
        // compares each appointment to a random non office hour time to determine if meeting extends over nonoffice hours
        // If the start time is before 3am and the end time is after 3 on the day of the meeting end, then it extends over
        // multiple days.
        LocalDate meetingEndDate = end.toLocalDate();
        LocalTime overlapLT = LocalTime.of(3,0); // 3am
        ZonedDateTime overlapZDT = ZonedDateTime.of(meetingEndDate, overlapLT, Schedule.getCompanyZoneId()); //converts to 3am eastern
        ZonedDateTime checkZDT = overlapZDT.withZoneSameInstant(Schedule.getUserZoneId()); // 3am eastern in user time
        LocalDateTime checkLDT = checkZDT.toLocalDateTime();
        if( start.isBefore(checkLDT) && end.isAfter(checkLDT)){
            warningMessage.append("\n" + "The meeting is scheduled outside office hours.");
            needsAlert = true;
        }

        //Checks each appointment for the user for overlaps.
        for (Appointment appointment : Schedule.getAllAppointments()){

            if (appointment.getCustomerID() == customerID) {
                LocalDateTime a = appointment.getStart();
                LocalDateTime b = appointment.getEnd();
                //Predicate lambdas
                Predicate<LocalDateTime> checkStart = i -> (i.isAfter(a) || i.isEqual(a)) && i.isBefore(b);
                Predicate<LocalDateTime> checkEnd = i -> i.isAfter(a) && (i.isBefore(b) || i.isEqual(b));
                BiPredicate<LocalDateTime, LocalDateTime> checkOverLap = (i, j) -> ((i.isBefore(a) || i.isEqual(a)) && (j.isAfter(b) || j.isEqual(b)));

                if ( (checkStart.test(start) ||checkEnd.test(end) || checkOverLap.test(start,end)) && (appointment.getAppointmentID() != appointmentID) ) {
                    warningMessage.append("\n" + "Appointment ID: " + appointment.getAppointmentID() +
                            " schedule from " + appointment.getStartTimeString() + " to " + appointment.getEndTimeString() +
                            " on " + appointment.getStartDate() + "\n");
                    needsAlert = true;
                }

            }
        }
        if (needsAlert)
            return "Unable to schedule due to following conflicts: " + "\n"+ warningMessage.toString();
        return warningMessage.toString();
    }


}
