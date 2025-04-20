import classes.Man;
import interfaces.Person;
import proxy.PersonInvocationHandler;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Man mohan = new Man("Raj", 27);
        ClassLoader classLoader = mohan.getClass().getClassLoader();
        Class[] interfaces = mohan.getClass().getInterfaces();
        Person proxyMohan = (Person) Proxy.newProxyInstance(classLoader, interfaces, new PersonInvocationHandler(mohan));
        proxyMohan.introduce(mohan.getName());
    }
}
