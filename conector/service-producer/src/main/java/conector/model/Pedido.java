package conector.model;

import java.math.BigDecimal;

public class Pedido {

    private final String userId, orderId;
    private final BigDecimal amount;

    public Pedido(String userId, String orderId, BigDecimal amount){
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount){
        amount = amount;
    }

}
