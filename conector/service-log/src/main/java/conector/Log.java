package conector;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class Log {

    public static void main(String[] args) throws IOException {

        var logService = new Log();
        try (var service = new KafkaService(Log.class.getSimpleName(),
                Pattern.compile(".*"),
                logService::parse,
                String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            service.run();
        }

        //var log = new Log();
        //var kfkService = new KafkaService(Log.class.getSimpleName(),"novo_pedido", log::parse);
        //kfkService.run();

        //var consumer = new KafkaConsumer<String, String>(properties());
       // consumer.subscribe(Pattern.compile(".*"));//.* determina que todos os tópicos serão observados

    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-----------------LOG-----------------------");
        System.out.println("Tópico: " + record.topic());
        System.out.println("Chave: " + record.key());
        System.out.println("Valor: " + record.value());
        System.out.println("Partição: " + record.partition());
        System.out.println("OffSet: " + record.offset());
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
