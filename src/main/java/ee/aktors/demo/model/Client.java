package ee.aktors.demo.model;

public class Client {

    private final long securityNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Country country;
    private String address;

    public Client() {
        this.securityNumber = System.currentTimeMillis();
    }
}
