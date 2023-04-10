package conector;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaService {

    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    public KafkaService(String topic, ConsumerFunction parse) throws InterruptedException {
        this.consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList(topic));

        while (true){
            var registros = consumer.poll(Duration.ofMillis(100));
            if(!registros.isEmpty()){
                System.out.println("Encontramos " + registros.count() + " registro(s)");

                for (var registro : registros){
                    parse.consume(registro);

                    Thread.sleep(5000);
                }

            }

        }
    }

    public void run(){

    }

    private static Properties properties(){
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.1:29092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, Log.class.getSimpleName());
        properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "1");//Determina que irá receber as mensagens uma vez apenas, de um em um registro
        return properties;
    }

}
