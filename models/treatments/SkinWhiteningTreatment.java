package SkinCareClinic.models.treatments;

public class SkinWhiteningTreatment implements Treatment {

    public SkinWhiteningTreatment() {}

    @Override
    public double getPrice() {
        return 7650.00;
    }

    @Override
    public String getTreatmentName() {
        return "Skin Whitening";
    }
}
