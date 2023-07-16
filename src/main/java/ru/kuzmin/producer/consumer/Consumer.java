package ru.kuzmin.producer.consumer;

import net.minidev.json.JSONObject;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import ru.kuzmin.producer.deserializer.CustomerDeserializer;
import ru.kuzmin.producer.model.Customer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

public class Consumer {
    public static void consume() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "CountryCounter");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "false");
        props.put("value.deserializer",
                CustomerDeserializer.class.getName());

        try (var consumer =
                     new KafkaConsumer<String, Customer>(props)) {
            //consumer.subscribe(Pattern.compile("Customer*"));
            consumer.subscribe(Collections.singletonList("customerCountries"));
            Duration timeout = Duration.ofMillis(100);
            while (true) {
                ConsumerRecords<String, Customer> records = consumer.poll(timeout);
                for (ConsumerRecord<String, Customer> record : records) {
                    System.out.println("current customer Id: " +
                            record.value().getID() + " and current customer name: " +
                            record.value().getName());
                }
                consumer.commitSync();
               /* consumer.commitAsync(new OffsetCommitCallback() {
                    public void onComplete(Map<TopicPartition,
                            OffsetAndMetadata> offsets, Exception e) {
                        if (e != null)
                            log.error("Commit failed for offsets {}", offsets, e);
                    }
                });*/
            }
        }
    }
}
