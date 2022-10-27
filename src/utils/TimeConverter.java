package utils;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Schedule;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;


/**Contains methods to convert between different time objects and provides the list of times for Add and Edit appointment controllers. */
public class TimeConverter {


    /** Converts UTC Timestamps into LocalDateTime Objects. Used to pull datetime from the database.
     * Contains some commented coded in case a different driver is used.
     * @param ts a TimeStamp in UTC from the database
     * @return the TimeStamp in LocalDateTime of the user
     */
    public static LocalDateTime utcToLocal(Timestamp ts){
        //LocalDateTime ldt = ts.toLocalDateTime();
        //ZonedDateTime serverZDT = ZonedDateTime.of(ldt, Schedule.getServerZoneId());
        //ZonedDateTime userZDT = serverZDT.withZoneSameInstant(Schedule.getUserZoneId());
        //LocalDateTime finalLDT = userZDT.toLocalDateTime();
        //return finalLDT;
        return ts.toLocalDateTime();
    }

    /**Converts LocalDateTime objects into UTC Timestamps. Used to store datetime in the database.
     * Contains some commented coded in case a different driver is used.
     * @param ldt LocalDateTime object
     * @return TimeStamp in UTC
     */
    public static Timestamp localToUTC(LocalDateTime ldt){
        //ZonedDateTime startZDT = ZonedDateTime.of(ldt ,Schedule.getUserZoneId());
        //Instant startGMTInstant = startZDT.toInstant();
       // LocalDateTime  instantToLDT= LocalDateTime.ofInstant(startGMTInstant, ZoneOffset.UTC);
        //Timestamp TS = Timestamp.valueOf(instantToLDT);
        //return TS;
        return Timestamp.valueOf(ldt);
    }

    /**Provides a list of times. The times are formatted as Strings and in local time for combo boxes when
     * creating and editing appointments.
     * @return a list of times.
     */
    public static ObservableList<String> getTimeList(){
        ObservableList<String> timeList = FXCollections.observableArrayList();
        LocalDate today = LocalDateTime.now().toLocalDate();
        DateTimeFormatter am = DateTimeFormatter.ofPattern("hh:mm a");

        LocalTime startLT = LocalTime.of(8,0); // 8am
        ZonedDateTime startZDT = ZonedDateTime.of(today, startLT, Schedule.getCompanyZoneId()); //converts to 8am eastern
        ZonedDateTime userZDT = startZDT.withZoneSameInstant(Schedule.getUserZoneId()); // 8am eastern in user time
        LocalDateTime userLDT = userZDT.toLocalDateTime(); // now just time (in user time)
        LocalDateTime endLDT = userLDT.plusHours(14); // 14 hours from start

        while(userLDT.isBefore(endLDT) || userLDT.equals(endLDT)){
            String time = am.format(userLDT.toLocalTime());
            timeList.add(time);
            userLDT = userLDT.plusMinutes(10);
        }
        return timeList;
    }
}
