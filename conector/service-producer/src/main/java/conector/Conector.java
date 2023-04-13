package conector;

import conector.model.Pedido;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Conector {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        try (var producer = new KafkaProducerService<Pedido>()){

            for(var i = 0; i < 10; i++){
                var chave = UUID.randomUUID().toString();
                var userId = UUID.randomUUID().toString();
                var valor = new BigDecimal(Math.random() * 5000 + 1);
                var ped = new Pedido(userId, chave, valor);
                producer.send("novo_pedido", chave, ped);
            }
        };

    }

}
