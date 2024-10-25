package SkinCareClinic.models;

import SkinCareClinic.models.treatments.Treatment;

public class Appointment {
    private static int idCounter = 1;
    private int appointmentID;
    private String date;
    private String time;
    private Patient patient;
    private Dermatologist dermatologist;
    private Treatment treatment;
    private final double registrationFee = 500;
    private double finalAmount = 0;

    public Appointment(String date, String time, Patient patient, Dermatologist dermatologist) {
        this.appointmentID = idCounter++;
        this.date = date;
        this.time = time;
        this.patient = patient;
        this.dermatologist = dermatologist;
        this.treatment = null;
    }

    public int getAppointmentID() { return appointmentID; }
    public Patient getPatient() { return patient; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public Dermatologist getDermatologist() { return dermatologist; }
    public Treatment getTreatment() { return treatment; }
    public double getFinalAmount() { return finalAmount; }

    public void addTreatment(Treatment treatment) {
        this.treatment = treatment;
        double treatmentFee = treatment.getPrice();
        double tax = treatmentFee * 0.025;
        this.finalAmount = Math.ceil(treatmentFee + tax + registrationFee);
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public boolean hasTreatment() {
        return treatment != null;
    }

    public static int getIdCounter() { return idCounter; }
    public static void setIdCounter(int idCounter) { Appointment.idCounter = idCounter; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setDermatologist(Dermatologist dermatologist) { this.dermatologist = dermatologist; }
    public void setTreatment(Treatment treatment) { this.treatment = treatment; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
}
