package ru.kuzmin.producer.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Admin {

    public static void listOfTopics() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        AdminClient admin = AdminClient.create(props);

        ListTopicsResult topics = admin.listTopics();
        var top = topics.names().get();
        top.forEach(System.out::println);

        admin.close(Duration.ofSeconds(30));
    }
}
