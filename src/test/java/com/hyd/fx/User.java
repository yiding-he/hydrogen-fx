package com.hyd.fx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private IntegerProperty id = new SimpleIntegerProperty();

    private StringProperty firstName = new SimpleStringProperty();

    private StringProperty lastName = new SimpleStringProperty();

    private ObjectProperty<UserType> userType = new SimpleObjectProperty<>();

    public User(int id, String firstName, String lastName, UserType userType) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUserType(userType);
    }

    public UserType getUserType() {
        return userType.get();
    }

    public Property<UserType> userTypeProperty() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType.set(userType);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}
