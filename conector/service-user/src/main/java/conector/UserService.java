package conector;

import conector.model.Pedido;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class UserService {

    private final Connection connection;

    UserService() throws SQLException {
        String url = "jdbc:sqlite:target/database/users_database.db";
        this.connection = DriverManager.getConnection(url);
        connection.createStatement().execute("create table if not exists Users(id varchar(200) primary key, email varchar(200))");
    }

    public static void main(String[] args) throws IOException, SQLException {
        var usrService = new UserService();
        try(var service = new KafkaService<>(UserService.class.getSimpleName(), "novo_pedido", usrService::parse, Pedido.class, Map.of())){
            service.run();
        }

    }

    private void parse(ConsumerRecord<String, Pedido> record) throws SQLException {
        System.out.println("-----------------User Log-----------------------");
        System.out.println("Tópico: " + record.topic());
        System.out.println("Chave: " + record.key());
        System.out.println("Valor: " + record.value());
        System.out.println("Partição: " + record.partition());
        System.out.println("OffSet: " + record.offset());

        var pedido = record.value();
        if(isNewUser(pedido.getUserEmail())){
            insert(pedido.getUserEmail());
        }

    }
    private boolean isNewUser(String userEmail) throws SQLException {
        var exists = connection.prepareStatement("select id from Users where email = ? limit 1");
        exists.setString(1, userEmail);
        var results = exists.executeQuery();
        return !results.next();
    }

    private void insert(String userEmail) throws SQLException {
        var insert = connection.prepareStatement("insert into Users (id, email) values (?, ?)");
        insert.setString(1, "id");
        insert.setString(1, userEmail);
        insert.execute();
        System.out.println("Usuario adicionado: " + userEmail);
    }

}
