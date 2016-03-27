package sample;

import java.util.GregorianCalendar;

/**
 * Created by wouter on 26-3-2016.
 */
public class Person {
    String name;
    String lastName;
    Group parent;

    public Person(String name, String lastName, Group parent) {
        this.name = name;
        this.lastName = lastName;
        this.parent = parent;
    }

    public Group getParent() {

        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
}
