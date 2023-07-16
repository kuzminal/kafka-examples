package ru.kuzmin.producer;

import static ru.kuzmin.producer.producer.Producer.produceCountry;
import static ru.kuzmin.producer.producer.Producer.produceCustomer;

public class App {
    public static void main(String[] args) {
        produceCountry();
        produceCustomer();
    }
}
