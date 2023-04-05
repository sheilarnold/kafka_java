package conector;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

public class Log {

    public static void main(String[] args) throws InterruptedException {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Pattern.compile(".*"));//.* determina que todos os tópicos serão observados

        while (true){
            var registros = consumer.poll(Duration.ofMillis(100));
            if(!registros.isEmpty()){
                System.out.println("Encontramos " + registros.count() + " registro(s)");

                for (var registro : registros){
                    System.out.println("-----------------LOG-----------------------");
                    System.out.println("Tópico: " + registro.topic());
                    System.out.println("Chave: " + registro.key());
                    System.out.println("Valor: " + registro.value());
                    System.out.println("Partição: " + registro.partition());
                    System.out.println("OffSet: " + registro.offset());

                    Thread.sleep(5000);
                }

            }

        }

    }

    private static Properties properties(){
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.1:29092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, Log.class.getSimpleName());
        return properties;
    }

}
