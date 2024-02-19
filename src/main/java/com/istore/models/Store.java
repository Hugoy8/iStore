package com.istore.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Store {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final ListProperty<String> employees = new SimpleListProperty<>();

    public Store(int id, String name, String location, List<String> employees) {
        this.id.set(id);
        this.name.set(name);
        this.location.set(location);
        this.employees.set(FXCollections.observableArrayList(employees));
    }

    public Store(String name, String location, List<String> employees) {
        this.name.set(name);
        this.location.set(location);
        this.employees.set(FXCollections.observableArrayList(employees));
    }

    // Getters and Setters for id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getters and Setters for name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Getters and Setters for location
    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    // Getters and Setters for employees
    public ObservableList<String> getEmployees() {
        return employees.get();
    }

    public void setEmployees(List<String> employees) {
        this.employees.set(FXCollections.observableArrayList(employees));
    }

    public ListProperty<String> employeesProperty() {
        return employees;
    }
}
