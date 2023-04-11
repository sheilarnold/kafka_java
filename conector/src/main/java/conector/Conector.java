package conector;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Conector {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        //var producer = new KafkaProducer<String, String>(properties());
        try (var producer = new KafkaProducerService()){

            for(var i = 0; i < 10; i++){
                //var record = new ProducerRecord<>("novo_pedido", UUID.randomUUID().toString(), "1923;123;253,90");
                producer.send("novo_pedido", UUID.randomUUID().toString(), "1923;123;253,90");
            }
        };

        /*producer.send(record, (data, ex) -> {
            if(ex != null){
                ex.printStackTrace();
                return;
            }
            //Informa que a mensagem está sendo enviada
            System.out.println(data.topic() + " :: " + data.partition() + "/" + data.offset() + "/" + data.timestamp());
        }).get();*/
    }

}
