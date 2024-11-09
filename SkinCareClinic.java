package SkinCareClinic;

import SkinCareClinic.services.Clinic;
import SkinCareClinic.models.Patient;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;

public class SkinCareClinic {

    public static void main(String[] args) {
        Clinic clinic = new Clinic();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Welcome to Aurora Skin Care Clinic ");

        while (true) {
            System.out.println("\n");
            System.out.println("1. Register an Appointment (Initial Registration Only)");
            System.out.println("2. Add Treatment to an Existing Appointment");
            System.out.println("3. Update Appointment Time");
            System.out.println("4. View Appointments by Date");
            System.out.println("5. View Appointments by Dermatologist Name");
            System.out.println("6. View Available Times for a Dermatologist");
            System.out.println("7. View Appointments by Patient NIC");
            System.out.println("8. View Appointments by Patient Name");
            System.out.println("9. View Appointment by Appointment ID");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter patient details:");

                    String nic = getValidNIC(scanner);
                    String name = getValidName(scanner);
                    String email = getValidEmail(scanner);
                    String phone = getValidPhoneNumber(scanner);

                    Patient patient = new Patient(nic, name, email, phone);

                    String date = getValidDay(scanner, "Enter preferred appointment date (e.g., Monday): ");
                    String time = getValidInput(scanner, "Enter preferred time (e.g., 10:00 AM): ");
                    String dermatologistName = getValidDoctor(scanner, "Choose Dermatologist (Dr.Doolittle or Dr.Sofia): ");

                    clinic.makeAppointment(date, time, patient, dermatologistName);
                    break;

                case 2:
                    int appointmentID;
                    try {
                        System.out.print("Enter Appointment ID: ");
                        appointmentID = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid appointment ID.");
                        continue;
                    }

                    System.out.println("Select Treatment:");
                    System.out.println("1. Acne Treatment");
                    System.out.println("2. Skin Whitening");
                    System.out.println("3. Mole Removal");
                    System.out.println("4. Laser Treatment");
                    System.out.print("Enter your choice: ");

                    int treatmentChoice;
                    try {
                        treatmentChoice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please select a number between 1 and 4.");
                        continue;
                    }

                    String treatmentName;
                    switch (treatmentChoice) {
                        case 1: treatmentName = "Acne Treatment"; break;
                        case 2: treatmentName = "Skin Whitening"; break;
                        case 3: treatmentName = "Mole Removal"; break;
                        case 4: treatmentName = "Laser Treatment"; break;
                        default:
                            System.out.println("Invalid treatment option.");
                        continue;
                    }

                    clinic.addTreatmentToAppointment(appointmentID, treatmentName);
                    break;

                case 3:
                    int updateAppointmentID;
                    try {
                        System.out.print("Enter Appointment ID to update: ");
                        updateAppointmentID = Integer.parseInt(getValidInput(scanner, "Enter appointment ID (e.g., 10): "));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid appointment ID.");
                        continue;
                    }

                    String newDate = getValidDay(scanner, "Enter new date for the appointment (e.g., Monday): ");
                    String newTime = getValidInput(scanner, "Enter new time for the appointment (e.g., 10:00 AM): ");

                    if (clinic.updateAppointmentTime(updateAppointmentID, newDate, newTime)) {
                        System.out.println("Appointment updated successfully.");
                    }
                    break;

                case 4:
                    String viewDate = getValidDay(scanner, "Enter date to view appointments: ");
                    clinic.viewAppointmentsByDate(viewDate);
                    break;

                case 5:
                    String dermatologist = getValidDoctor(scanner, "Enter dermatologist name to view appointments: ");
                    clinic.findAppointmentsByDermatologistName(dermatologist);
                    break;

                case 6:
                    String dermName = getValidDoctor(scanner, "Enter Dermatologist name (Dr.Doolittle or Dr.Sofia): ");
                    String day = getValidDay(scanner, "Enter day (e.g., Monday): ");
                    clinic.viewAvailableTimesForDermatologist(dermName, day);
                    break;
                case 7:
                    String patientNIC = getValidInput(scanner,"Enter patient's NIC to find appointments:");
                    clinic.findAppointmentsByPatientNIC(patientNIC);
                    break;
                case 8:
                    String patientName = getValidInput(scanner,"Enter patient's name to find appointments: ");
                    clinic.findAppointmentsByPatientName(patientName);
                    break;

                case 9:
                    System.out.print("Enter appointment ID to view details: ");
                    try {
                        int searchAppointmentID = Integer.parseInt(scanner.nextLine());
                        clinic.findAppointmentByAppointmentID(searchAppointmentID);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid appointment ID.");
                    }
                    break;
                case 10:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static String getValidInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("This field cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static String getValidNIC(Scanner scanner) {
        String nic;
        Pattern nicPattern = Pattern.compile("^(\\d{9}|\\d{11})V$");
        do {
            System.out.print("NIC (9 or 11 numeric characters followed by 'V'): ");
            nic = scanner.nextLine().trim();
            if (!nicPattern.matcher(nic).matches()) {
                System.out.println("Invalid NIC. It must have 9 or 11 numeric characters followed by 'V'.");
            }
        } while (!nicPattern.matcher(nic).matches());
        return nic;
    }

    private static String getValidPhoneNumber(Scanner scanner) {
        String phone;
        do {
            System.out.print("Phone (10 digits): ");
            phone = scanner.nextLine().trim();
            if (!phone.matches("\\d{10}")) {
                System.out.println("Invalid phone number. It should be exactly 10 digits.");
            }
        } while (!phone.matches("\\d{10}"));
        return phone;
    }

    private static String getValidEmail(Scanner scanner) {
        String email;
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        } while (!emailPattern.matcher(email).matches());
        return email;
    }

    private static String getValidName(Scanner scanner) {
        String name;
        do {
            System.out.print("Name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
            }
        } while (name.isEmpty());
        return name;
    }

    private static String getValidDoctor(Scanner scanner, String prompt) {
        List<String> validDermatologists = Arrays.asList("Dr.Doolittle", "Dr.Sofia");
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!validDermatologists.contains(input)) {
                System.out.println("Invalid dermatologist name. Please choose from Dr.Doolittle or Dr.Sofia ");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }

    private static String getValidDay(Scanner scanner, String prompt) {
        List<String> validDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!validDays.contains(input)) {
                System.out.println("Invalid day. Please enter a valid day (e.g. Monday).");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }
}
