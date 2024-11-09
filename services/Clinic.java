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
        dermatologists.add(new Dermatologist("Dr.Doolittle", "doolittle@gmail.com", "0701636952"));
        dermatologists.add(new Dermatologist("Dr.Sofia", "sofia@gmail.com", "0749652364"));
    }

    public void makeAppointment(String date, String time, Patient patient, String dermatologistName) {
        Dermatologist dermatologist = findDermatologistByName(dermatologistName);

        if (dermatologist != null) {
            if (dermatologist.isTimeAvailable(date, time)) {
                Appointment newAppointment = new Appointment(date, time, patient, dermatologist);
                appointments.add(newAppointment);
                dermatologist.markTimeAsUnavailable(date, time);
                System.out.println("=========================================================================");
                System.out.println("Appointment registered successfully with a registration fee of LKR 500!");
                System.out.println("Appointment ID: " + newAppointment.getAppointmentID());
                System.out.println("=========================================================================");
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
            printAppointmentDetails(appointment);
        } else {
            System.out.println("Failed to add treatment. Please check the appointment ID or treatment name.");
        }
    }

    public Appointment findAppointmentByID(int appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;
    }

    public void findAppointmentsByPatientNIC(String nic) {
        List<Appointment> matchingAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getNic().equalsIgnoreCase(nic)) {
                matchingAppointments.add(appointment);
            }
        }
        if (matchingAppointments.isEmpty()) {
            System.out.println("No appointments found for the provided NIC.");
        } else {
            for (Appointment appointment : matchingAppointments) {
                printAppointmentDetails(appointment);
            }
        }
    }

    private Dermatologist findDermatologistByName(String name) {
        for (Dermatologist derm : dermatologists) {
            if (derm.getName().equalsIgnoreCase(name)) {
                return derm;
            }
        }
        return null;
    }

    public void findAppointmentsByDermatologistName(String name) {
        boolean found = false;
        for (Appointment appointment : appointments) {
            if (appointment.getDermatologist().getName().equalsIgnoreCase(name)) {
                printAppointmentDetails(appointment);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found for the provided patient name.");
        }
    }

    public void findAppointmentsByPatientName(String name) {
        boolean found = false;
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getName().equalsIgnoreCase(name)) {
                printAppointmentDetails(appointment);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found for the provided patient name.");
        }
    }

    public void findAppointmentByAppointmentID(int appointmentID) {
        Appointment appointment = findAppointmentByID(appointmentID);
        if (appointment != null) {
            printAppointmentDetails(appointment);
        } else {
            System.out.println("No appointment found with the provided ID.");
        }
    }

    public void viewAppointmentsByDate(String date) {
        System.out.println("Appointments for " + date + ":");
        boolean hasAppointments = false;

        for (Appointment appointment : appointments) {
            if (appointment.getDate().equalsIgnoreCase(date)) {
                printAppointmentDetails(appointment);
                hasAppointments = true;
            }
        }

        if (!hasAppointments) {
            System.out.println("No appointments found for this date.");
        }
    }

    private void printAppointmentDetails(Appointment appointment) {
        System.out.println(
                "========================================\n"+
                "Appointment ID: " + appointment.getAppointmentID()+'\n'+
                "Date : " + appointment.getDate() +'\n'+
                "Time : " + appointment.getTime()+'\n' +
                "Patient : " + appointment.getPatient().getName() +'\n' +
                "Dermatologist : " + appointment.getDermatologist().getName() +'\n' +
                "Treatment : " + (appointment.getTreatment() != null ? appointment.getTreatment().getTreatmentName() +" :  "+ appointment.getTreatment().getPrice() : "No treatment assigned") +'\n'+
                "Sub Total : " + (appointment.getSubAmount() != 0 ? appointment.getSubAmount(): "0.00") +'\n'+
                "Tax (2.5%) : " + (appointment.getTaxAmount() != 0 ? appointment.getTaxAmount(): "0.00")+ '\n' +
                "Registration Fee : " + (appointment.getRegistrationFee()) +'\n'+
                "Total Price : " + (appointment.getFinalAmount() != 0 ? appointment.getFinalAmount(): "0.00")+'\n'+
                "========================================\n");
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

    public boolean updateAppointmentTime(int appointmentID, String newDate, String newTime) {
        Appointment appointment = findAppointmentByID(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }
        if (appointment.getFinalAmount() > 0) {
            System.out.println("Appointment cannot be updated as it already has a complete.");
            return false;
        }
        Dermatologist dermatologist = appointment.getDermatologist();
        if (!isTimeSlotAvailable(newDate, newTime, dermatologist)) {
            System.out.println("The new time slot is not available. Please choose a different time.");
            return false;
        }
        makeTimeSlotAvailable(appointment.getDate(), appointment.getTime(), dermatologist);
        appointment.setDate(newDate);
        appointment.setTime(newTime);
        dermatologist.markTimeAsUnavailable(newDate, newTime);
        System.out.println("Appointment time updated successfully.");
        return true;
    }

    public boolean isTimeSlotAvailable(String date, String time, Dermatologist dermatologist) {
        return dermatologist.isTimeSlotAvailable(date, time);
    }

    public void makeTimeSlotAvailable(String date, String time, Dermatologist dermatologist) {
        dermatologist.markTimeAsAvailable(date, time);
    }
}
