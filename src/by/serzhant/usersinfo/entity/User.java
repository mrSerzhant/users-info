package by.serzhant.usersinfo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String firstName;
    private String lastName;
    private List<String> listPhoneNumber;
    private List<Role> roles;
    private String email;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        listPhoneNumber = new ArrayList<>(3);
        roles = new ArrayList<>(2);
    }

    public User(String firstName, String lastName, String email, String phoneNumber, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        listPhoneNumber = new ArrayList<>(3);
        listPhoneNumber.add(phoneNumber);
        roles = new ArrayList<>(2);
        roles.add(role);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getListPhoneNumber() {
        return listPhoneNumber;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addPhoneNumber(String phoneNumber) {
        listPhoneNumber.add(phoneNumber);
    }

    public void changePhoneNumber(int position, String phoneNumber) {
        listPhoneNumber.set(position, phoneNumber);
    }

    public void removePhoneNumber(int position) {
        listPhoneNumber.remove(position);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(int position) {
        roles.remove(position);
    }

    public void changeRole(int position, Role role) {
        roles.set(position, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(listPhoneNumber, user.listPhoneNumber) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, listPhoneNumber, roles, email);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + email + ", " + listPhoneNumber + ", " + roles;

    }
}
