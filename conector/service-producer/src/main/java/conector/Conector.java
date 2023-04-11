package conector;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Conector {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        try (var producer = new KafkaProducerService()){

            for(var i = 0; i < 10; i++){
                producer.send("novo_pedido", UUID.randomUUID().toString(), "1923;123;253,90");
            }
        };

    }

}
