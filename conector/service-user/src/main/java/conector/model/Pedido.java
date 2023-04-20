package conector.model;

import java.math.BigDecimal;

public class Pedido {

    private final String userId, orderId, user_email;
    private final BigDecimal amount;

    public Pedido(String userId, String orderId, String user_email, BigDecimal amount){
        this.userId = userId;
        this.orderId = orderId;
        this.user_email = user_email;
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

    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String user_email) {
        user_email = user_email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount){
        amount = amount;
    }

}
