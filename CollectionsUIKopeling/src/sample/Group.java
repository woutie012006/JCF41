package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wouter on 26-3-2016.
 */
public class Group {
    List<Person> employees= new ArrayList<>();
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group(List<Person> employees) {
        this.employees = employees;
    }

    public List<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Person> employees) {
        this.employees = employees;
    }

    public void addEmployee(Person p){
        employees.add(p);
    }
    public Group(String name) {
        this.name = name;
    }
}
