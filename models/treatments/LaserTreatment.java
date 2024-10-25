package SkinCareClinic.models.treatments;

public class LaserTreatment implements Treatment {

    public LaserTreatment() {}

    @Override
    public double getPrice() {
        return 12500.00;
    }

    @Override
    public String getTreatmentName() {
        return "Laser Treatment";
    }
}
