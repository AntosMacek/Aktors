package ee.aktors.demo.model;

public class Client {

    private long securityNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Country country;
    private String address;
    private String representer;

    public Client() {
    }

    public Client(long securityNumber, String firstName, String lastName, String phoneNumber, Country country, String address) {
        this.securityNumber = securityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.address = address;
    }

    public long getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(long securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClientName() {
        return firstName + " " + lastName;
    }

    public String getRepresenter() {
        return representer;
    }

    public void setRepresenter(String representer) {
        this.representer = representer;
    }

    @Override
    public String toString() {
        return "Client{" +
                "securityNumber=" + securityNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
