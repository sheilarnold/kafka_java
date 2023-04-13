package conector;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;
import java.util.regex.Pattern;

public class Log {

    public static void main(String[] args) throws InterruptedException {

        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Pattern.compile(".*"));//.* determina que todos os tópicos serão observados

        var log = new Log();
        var kfkService = new KafkaService(Log.class.getSimpleName(),"novo_pedido", log::parse);
        kfkService.run();

    }

    private void parse(ConsumerRecord<String, String> registro){
        System.out.println("-----------------LOG-----------------------");
        System.out.println("Tópico: " + registro.topic());
        System.out.println("Chave: " + registro.key());
        System.out.println("Valor: " + registro.value());
        System.out.println("Partição: " + registro.partition());
        System.out.println("OffSet: " + registro.offset());
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
