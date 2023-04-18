package conector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {

    public static final String TYPE_CONFIG = "conector.type_config";//String qualquer
    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));//Se existir valor, retorna o valor, senao, retorna nulo
        try {
            this.type = (Class<T>) Class.forName(typeName);//transforma typeName em uma classe
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("O tipo de desserialização não existe no classpath: " + e);
        }
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes), type);
    }

}
