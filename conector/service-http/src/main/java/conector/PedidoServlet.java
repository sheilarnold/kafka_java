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

    private final KafkaProducerService<Pedido> producer = new KafkaProducerService<>();

    @Override
    public void destroy() {
        super.destroy();
        try {
            producer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var email = req.getParameter("email");
            var chave = UUID.randomUUID().toString();
            var userId = UUID.randomUUID().toString();
            var valor = new BigDecimal(req.getParameter("valor"));
            var ped = new Pedido(userId, chave, valor, email);
            producer.send("novo_pedido", chave, ped);

            resp.getWriter().println("Pedido enviado");
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
