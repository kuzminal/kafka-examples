package ru.kuzmin.producer.deserializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import ru.kuzmin.producer.model.Customer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomerDeserializer implements Deserializer<Customer> {
    @Override
    public void configure(Map configs, boolean isKey) {
// настраивать нечего
    }

    @Override
    public Customer deserialize(String topic, byte[] data) {
        int id;
        int nameSize;
        String name;
        try {
            if (data == null)
                return null;
            if (data.length < 8)
                throw new SerializationException("Size of data received" +
                        "by deserializer is shorter than expected");
            ByteBuffer buffer = ByteBuffer.wrap(data);
            id = buffer.getInt();
            nameSize = buffer.getInt();
            byte[] nameBytes = new byte[nameSize];
            buffer.get(nameBytes);
            name = new String(nameBytes, StandardCharsets.UTF_8);
            return new Customer(id, name);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing " +
                    "byte[] to Customer " + e);
        }
    }

    @Override
    public void close() {
// закрывать нечего
    }
}
