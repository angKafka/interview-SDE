package classes;

import interfaces.Person;

public class Man implements Person {
    private String name;
    private int age;
    public Man(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String introduce(String name) {
        return String.format("%s is %s years old.", name, age);
    }

    @Override
    public String sayAge(int age) {
        return String.format("I am %s years old.", age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
