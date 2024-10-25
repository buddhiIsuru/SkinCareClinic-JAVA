package SkinCareClinic.models.treatments;

public class AcneTreatment implements Treatment {

    public AcneTreatment() {}

    @Override
    public double getPrice() {
        return 2750.00;
    }

    @Override
    public String getTreatmentName() {
        return "Acne Treatment";
    }
}
