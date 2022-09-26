package models;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private static final String emailRegEx = "^(.+)@(.+).(.+)$";

    public Customer(String firstName, String lastName, String email) {
        this.isEmailValid(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    //check input email is valid using regular expressions
    private void isEmailValid(String email) {
        Pattern emailPattern = Pattern.compile(emailRegEx, Pattern.CASE_INSENSITIVE);
        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException(email + " is not valid");
        }
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Customer: " + firstName.toUpperCase() + " " + lastName.toUpperCase() + " - " + email.toLowerCase() + "\n";
    }
}
