package ru.tfs.collections_generics.task3;

import java.util.List;

public class ClientTask3 {
    private int id;
    private String name;
    private int age;
    private List<PhoneTask3> phones;

    public ClientTask3(int id, String name, int age, List<PhoneTask3> phones) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phones = phones;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<PhoneTask3> getPhones() {
        return phones;
    }
}

enum TelephoneTypeTask3 {

    HOME_PHONE("homePhone"),
    MOBILE_PHONE("MobilePhone");

    private String type;

    TelephoneTypeTask3(String type) {
        this.type = type;
    }
}

class PhoneTask3 {
    private TelephoneTypeTask3 type;
    private String number;

    public PhoneTask3(TelephoneTypeTask3 type, String number) {
        this.type = type;
        this.number = number;
    }

    public TelephoneTypeTask3 getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }
}
