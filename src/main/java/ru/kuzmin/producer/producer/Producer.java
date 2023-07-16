package ru.kuzmin.producer.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.kuzmin.producer.interceptor.CountingProducerInterceptor;
import ru.kuzmin.producer.model.Customer;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Producer {
    public static void produceCountry() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:29092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("acks", "all");
        kafkaProps.put("retries", "3");
        kafkaProps.put("max.in.flight.requests.per.connection", "1");
        try (var producer = new KafkaProducer<String, String>(kafkaProps)) {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("CustomerCountry", "Precision Products",
                            "France");
            try {
                var res = producer.send(record).get();
                System.out.println(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void produceCustomer() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:29092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer",
                "ru.kuzmin.producer.serializer.CustomerSerializer");
        kafkaProps.put("acks", "all");
        kafkaProps.put("retries", "3");
        kafkaProps.put("max.in.flight.requests.per.connection", "1");
        kafkaProps.put("partitioner.class", "ru.kuzmin.producer.partitioner.BananaPartitioner"); // свой маршрутизатор по партициям
        kafkaProps.put("interceptor.classes",
                CountingProducerInterceptor.class.getName());
        kafkaProps.put("counting.interceptor.window.size.ms", "20");
        try (var producer = new KafkaProducer<String, Customer>(kafkaProps)) {
            ProducerRecord<String, Customer> record =
                    new ProducerRecord<>("Customers", "First Cust",
                            new Customer(1, "Cust One"));
            record.headers().add("privacy-level", "YOLO".getBytes(StandardCharsets.UTF_8));
            try {
                var res = producer.send(record).get();
                System.out.println(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
