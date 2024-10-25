package SkinCareClinic.services;

import SkinCareClinic.models.*;
import SkinCareClinic.models.treatments.*;
import java.util.ArrayList;
import java.util.List;

public class Clinic {
    private final List<Dermatologist> dermatologists;
    private final List<Appointment> appointments;

    public Clinic() {
        dermatologists = new ArrayList<>();
        appointments = new ArrayList<>();
        initializeDermatologists();
    }

    private void initializeDermatologists() {
        dermatologists.add(new Dermatologist("Dr. Smith", "smith@example.com", "0123456789"));
        dermatologists.add(new Dermatologist("Dr. Johnson", "johnson@example.com", "9876543210"));
    }

    public void makeAppointment(String date, String time, Patient patient, String dermatologistName) {
        Dermatologist dermatologist = findDermatologistByName(dermatologistName);

        if (dermatologist != null) {
            if (dermatologist.isTimeAvailable(date, time)) {
                Appointment newAppointment = new Appointment(date, time, patient, dermatologist);
                appointments.add(newAppointment);
                dermatologist.markTimeAsUnavailable(date, time);
                System.out.println("Appointment registered successfully with a registration fee of LKR 500!");
                System.out.println("Appointment ID: " + newAppointment.getAppointmentID());
            } else {
                System.out.println("Selected time slot is unavailable. Please choose another time.");
            }
        } else {
            System.out.println("Failed to register appointment. Please check details.");
        }
    }

    public void addTreatmentToAppointment(int appointmentID, String treatmentName) {
        Appointment appointment = findAppointmentByID(appointmentID);
        Treatment treatment = findTreatmentByName(treatmentName);

        if (appointment != null && treatment != null) {
            appointment.addTreatment(treatment);
            System.out.println("Treatment added successfully!");
            System.out.println("Final amount with 2.5% tax: LKR " + appointment.getFinalAmount());
        } else {
            System.out.println("Failed to add treatment. Please check the appointment ID or treatment name.");
        }
    }

    private Appointment findAppointmentByID(int appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;
    }

    private Dermatologist findDermatologistByName(String name) {
        for (Dermatologist derm : dermatologists) {
            if (derm.getName().equalsIgnoreCase(name)) {
                return derm;
            }
        }
        return null;
    }

    public void viewAppointmentsByDate(String date) {
        System.out.println("Appointments for " + date + ":");
        boolean hasAppointments = false;

        for (Appointment appointment : appointments) {
            if (appointment.getDate().equalsIgnoreCase(date)) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                        ", Patient: " + appointment.getPatient().getName() +
                        ", Time: " + appointment.getTime() +
                        ", Dermatologist: " + appointment.getDermatologist().getName() +
                        ", Treatment: " + appointment.getTreatment().getTreatmentName());
                hasAppointments = true;
            }
        }

        if (!hasAppointments) {
            System.out.println("No appointments found for this date.");
        }
    }

    public void viewAvailableTimesForDermatologist(String dermatologistName, String day) {
        Dermatologist dermatologist = findDermatologistByName(dermatologistName);
        if (dermatologist != null) {
            List<Availability> availableTimes = dermatologist.getAvailableTimesForDay(day);
            System.out.println("Available times for " + dermatologistName + " on " + day + ":");
            for (Availability slot : availableTimes) {
                if (slot.isAvailable()) {
                    System.out.println(slot.getTime());
                }
            }
        } else {
            System.out.println("Dermatologist not found.");
        }
    }

    private Treatment findTreatmentByName(String treatmentName) {
        switch (treatmentName.toLowerCase()) {
            case "acne treatment":
                return new AcneTreatment();
            case "skin whitening":
                return new SkinWhiteningTreatment();
            case "mole removal":
                return new MoleRemovalTreatment();
            case "laser treatment":
                return new LaserTreatment();
            default:
                return null;
        }
    }
}
