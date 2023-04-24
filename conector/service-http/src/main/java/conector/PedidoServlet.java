package conector;

import conector.model.Pedido;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class PedidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var producer = new KafkaProducerService<Pedido>()) {
            var chave = UUID.randomUUID().toString();
            var userId = UUID.randomUUID().toString();
            var valor = new BigDecimal(Math.random() * 5000 + 1);
            var ped = new Pedido(userId, chave, valor, Math.random() + "@email.com");
            producer.send("novo_pedido", chave, ped);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
