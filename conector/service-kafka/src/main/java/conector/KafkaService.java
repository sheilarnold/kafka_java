package conector;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

public class KafkaService implements Closeable {

    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    public KafkaService(String groupId, String topic, ConsumerFunction parse) {
        this(parse, groupId);
        consumer.subscribe(Collections.singletonList(topic));
    }

    public KafkaService(String groupId, Pattern topic, ConsumerFunction parse) {
        this(parse, groupId);
        consumer.subscribe(topic);
    }

    private KafkaService(ConsumerFunction parse, String groupId) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(groupId));
    }

    void run(){
        while (true){
            var registros = consumer.poll(Duration.ofMillis(100));
            if(!registros.isEmpty()){
                System.out.println("Encontramos " + registros.count() + " registro(s)");

                for (var registro : registros){
                    parse.consume(registro);
                }

            }

        }
    }

    private static Properties properties(String groupId){
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.1:29092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "1");//Determina que irá receber as mensagens uma vez apenas, de um em um registro
        return properties;
    }

    @Override
    public void close() throws IOException {
        consumer.close();
    }
}
