package SkinCareClinic.models;

public class Availability {
    private String time;
    private boolean isAvailable;

    public Availability(String time, boolean isAvailable) {
        this.time = time;
        this.isAvailable = isAvailable;
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
