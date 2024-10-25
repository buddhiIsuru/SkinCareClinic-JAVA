package SkinCareClinic.models.treatments;

public class MoleRemovalTreatment implements Treatment {

    public MoleRemovalTreatment() {}

    @Override
    public double getPrice() {
        return 3850.00;
    }

    @Override
    public String getTreatmentName() {
        return "Mole Removal";
    }
}
