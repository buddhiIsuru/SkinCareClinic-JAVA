package SkinCareClinic;

import SkinCareClinic.services.Clinic;
import SkinCareClinic.models.Patient;
import java.util.Scanner;

public class SkinCareClinic {

    public static void main(String[] args) {
        Clinic clinic = new Clinic();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to Aurora Skin Care Clinic");

        while (true) {
            System.out.println("\n");
            System.out.println("1. Register an Appointment (Initial Registration Only)");
            System.out.println("2. Add Treatment to an Existing Appointment");
            System.out.println("3. View Appointments by Date");
            System.out.println("4. View Available Times for a Dermatologist");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter patient details:");
                    System.out.print("NIC: ");
                    String nic = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();

                    Patient patient = new Patient(nic, name, email, phone);

                    System.out.print("Enter preferred appointment date (e.g., Monday): ");
                    String date = scanner.nextLine();

                    System.out.print("Enter preferred time: ");
                    String time = scanner.nextLine();

                    System.out.print("Choose Dermatologist (Dr. Smith or Dr. Johnson): ");
                    String dermatologistName = scanner.nextLine();

                    clinic.makeAppointment(date, time, patient, dermatologistName);
                    break;

                case 2:
                    System.out.print("Enter Appointment ID: ");
                    int appointmentID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Select Treatment:");
                    System.out.println("1. Acne Treatment");
                    System.out.println("2. Skin Whitening");
                    System.out.println("3. Mole Removal");
                    System.out.println("4. Laser Treatment");
                    System.out.print("Enter your choice: ");
                    int treatmentChoice = scanner.nextInt();
                    scanner.nextLine();

                    String treatmentName = "";
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
                    System.out.print("Enter date to view appointments: ");
                    String viewDate = scanner.nextLine();
                    clinic.viewAppointmentsByDate(viewDate);
                    break;

                case 4:
                    System.out.print("Enter Dermatologist name (Dr. Smith or Dr. Johnson): ");
                    String dermName = scanner.nextLine();
                    System.out.print("Enter day (e.g., Monday): ");
                    String day = scanner.nextLine();
                    clinic.viewAvailableTimesForDermatologist(dermName, day);
                    break;

                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

