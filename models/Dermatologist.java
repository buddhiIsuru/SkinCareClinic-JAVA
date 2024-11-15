package SkinCareClinic.models;

import SkinCareClinic.utilities.TimeSlotGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Dermatologist extends Person {
    private final Map<String, List<Availability>> availableTimes;

    public Dermatologist(String name, String email, String phone) {
        super(name, email, phone);
        this.availableTimes = new HashMap<>();
        initializeAvailableTimes();
    }

    private void initializeAvailableTimes() {
        availableTimes.put("Monday", TimeSlotGenerator.getConsultationDatesTimes("10:00 AM", "01:00 PM"));
        availableTimes.put("Wednesday", TimeSlotGenerator.getConsultationDatesTimes("02:00 PM", "05:00 PM"));
        availableTimes.put("Friday", TimeSlotGenerator.getConsultationDatesTimes("04:00 PM", "08:00 PM"));
        availableTimes.put("Saturday", TimeSlotGenerator.getConsultationDatesTimes("09:00 AM", "01:00 PM"));
    }

    public List<Availability> getAvailableTimesForDay(String day) {
        return availableTimes.get(day);
    }

    public boolean hasScheduleForDate(String date) {
        return availableTimes.containsKey(date);
    }

    public void markTimeAsUnavailable(String day, String time) {
        List<Availability> slots = availableTimes.get(day);
        if (slots != null) {
            for (Availability slot : slots) {
                if (slot.getTime().equals(time)) {
                    slot.setAvailable(false);
                    break;
                }
            }
        }
    }

    public boolean isTimeSlotAvailable(String date, String time) {
        List<Availability> bookedTimes = availableTimes.getOrDefault(date, new ArrayList<>());
        return !bookedTimes.contains(time);
    }

    public void markTimeAsAvailable(String date, String time) {
        if (availableTimes.containsKey(date)) {
            List<Availability> bookedTimes = availableTimes.get(date);
            bookedTimes.remove(time);
            if (bookedTimes.isEmpty()) {
                availableTimes.remove(date);
            }
        }
    }

    public boolean isTimeAvailable(String day, String time) {
        List<Availability> slots = availableTimes.get(day);
        if (slots != null) {
            for (Availability slot : slots) {
                if (slot.getTime().equals(time) && slot.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }
}
