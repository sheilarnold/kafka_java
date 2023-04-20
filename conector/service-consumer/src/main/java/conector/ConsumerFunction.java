package conector;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.SQLException;

public interface ConsumerFunction<T> {
    void consume(ConsumerRecord<String, T> record) throws SQLException;
}
