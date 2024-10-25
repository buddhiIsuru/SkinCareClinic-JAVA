package SkinCareClinic.models;

public class Patient extends Person {
    private String nic;

    public Patient(String nic, String name, String email, String phone) {
        super(name, email, phone);
        this.nic = nic;
    }

    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }
}
