package SkinCareClinic.utilities;

import SkinCareClinic.models.Availability;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimeSlotGenerator {

    public static ArrayList<Availability> getConsultationDatesTimes(String timeStart, String timeEnd) {
        ArrayList<Availability> availabilityTimeList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime startTime = LocalTime.parse(timeStart, formatter);
        LocalTime endTime = LocalTime.parse(timeEnd, formatter);

        while (startTime.isBefore(endTime)) {
            availabilityTimeList.add(new Availability(startTime.format(formatter), true));
            startTime = startTime.plusMinutes(15);
        }
        return availabilityTimeList;
    }
}
