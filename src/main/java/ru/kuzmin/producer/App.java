package ru.kuzmin.producer;

import java.util.concurrent.ExecutionException;

import static ru.kuzmin.producer.admin.Admin.listOfTopics;
import static ru.kuzmin.producer.consumer.Consumer.consume;
import static ru.kuzmin.producer.producer.Producer.produceCountry;
import static ru.kuzmin.producer.producer.Producer.produceCustomer;

public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        produceCountry();
        produceCustomer();
        //consume();
        listOfTopics();
    }
}
