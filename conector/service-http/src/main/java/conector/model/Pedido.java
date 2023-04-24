package conector.model;

import java.math.BigDecimal;

public class Pedido {

    private final String userId, orderId, user_email;
    private final BigDecimal amount;

    public Pedido(String userId, String orderId, BigDecimal amount, String user_email){
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.user_email = user_email;
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

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        user_email = user_email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount){
        amount = amount;
    }

}
