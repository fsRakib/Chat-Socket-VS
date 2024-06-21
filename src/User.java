public class User {
    private String username;
    private String fullName;
    private String phoneNumber;
    private String email;

    public User(String username, String fullName, String phoneNumber, String email) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
