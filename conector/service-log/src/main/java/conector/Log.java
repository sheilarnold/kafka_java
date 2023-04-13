package conector;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public class Log {

    public static void main(String[] args) throws IOException {

        var serviceLog = new Log();

        try(var service = new KafkaService(Log.class.getSimpleName(), Pattern.compile(".*"), serviceLog::parse)){
            service.run();
        }

        /*var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Pattern.compile(".*"));//.* determina que todos os tópicos serão observados

        var log = new Log();
        var kfkService = new KafkaService(Log.class.getSimpleName(),"novo_pedido", log::parse);
        kfkService.run();*/

    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-----------------LOG-----------------------");
        System.out.println("Tópico: " + record.topic());
        System.out.println("Chave: " + record.key());
        System.out.println("Valor: " + record.value());
        System.out.println("Partição: " + record.partition());
        System.out.println("OffSet: " + record.offset());
    }

    /*private void parse(ConsumerRecord<String, String> registro){
        System.out.println("-----------------LOG-----------------------");
        System.out.println("Tópico: " + registro.topic());
        System.out.println("Chave: " + registro.key());
        System.out.println("Valor: " + registro.value());
        System.out.println("Partição: " + registro.partition());
        System.out.println("OffSet: " + registro.offset());
    }*/

}
