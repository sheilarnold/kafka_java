package conector;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpService {

    public static void main(String[] args) throws Exception {
        var server = new Server(8001);

        var context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new PedidoServlet()), "/criar");

        server.setHandler(context);

        server.start();
        server.join();
    }

}
