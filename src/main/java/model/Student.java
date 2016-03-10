package model;

import java.util.Collection;

/**
 * Created by joshuapro on 09/03/16.
 */
public class Student {


    private String name;
    private long rate;
    private Address address;

    private Collection<String> departments;


    public Student() {

    }

    /**
     * @param name
     * @param rate
     * @param address
     * @param departments
     */

    public Student(String name, long rate, Address address, Collection<String> departments) {
        this.name = name;
        this.rate = rate;
        this.address = address;
        this.departments = departments;

    }

    public Address getAddress() {
        return address;
    }

    public Collection<String> getDepartments() {
        return departments;
    }

    public long getRate() {

        return rate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Ratings{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                ", address=" + address +
                ", departments=" + departments +
                '}';
    }
}
