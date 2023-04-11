package conector;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerService implements Closeable {

    private final KafkaProducer<String, String> producer;
    KafkaProducerService(){
        this.producer = new KafkaProducer<>(properties());
    }

    public void send(String topic, String key, String value) throws ExecutionException, InterruptedException {
        var record = new ProducerRecord<>(topic, key, value);

        Callback callback = (data, ex) -> {
            if(ex != null){
                ex.printStackTrace();
                return;
            }
            System.out.println(data.topic() + " :: " + data.partition() + "/" + data.offset() + "/" + data.timestamp());
        };

        producer.send(record, callback).get();

    }

    private static Properties properties(){
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.1:29092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    @Override
    public void close() throws IOException{
        producer.close();
    }

}
